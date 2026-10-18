abstract class Compte(
    val code: String,
    var solde: Double,
    var statut: StatutDeCompte = StatutDeCompte.CREATED
) : ICompte {

    val operations = mutableListOf<Operation>()

    override fun deposer(montant: Double) {
        require(montant > 0)
        check(statut == StatutDeCompte.CREATED)

        solde += montant
        operations.add(Versement(montant))
    }

    override fun retirer(montant: Double) {
        require(montant > 0)
        check(statut == StatutDeCompte.CREATED)
        check(autoriserRetrait(montant))

        solde -= montant
        operations.add(Retrait(montant))
    }

    abstract fun autoriserRetrait(montant: Double): Boolean

    override fun consulterSolde() = solde

    fun changerStatut(nouveau: StatutDeCompte) {
        check(statut != StatutDeCompte.DELETED)
        statut = nouveau
    }

    fun resume(): String {
        return "Compte(code=$code, solde=$solde, statut=$statut)"
    }
}

class CompteCourant(
    code: String,
    solde: Double,
    val decouvert: Double = 0.0
) : Compte(code, solde) {

    override fun autoriserRetrait(montant: Double) =
        montant <= solde + decouvert
}

class CompteEpargne(
    code: String,
    solde: Double,
    val tauxInteret: Double
) : Compte(code, solde) {

    override fun autoriserRetrait(montant: Double) =
        solde >= montant

    fun ajouterInterets() {
        solde += solde * tauxInteret
    }
}

interface ICompte {
    fun deposer(montant: Double)
    fun retirer(montant: Double)
    fun consulterSolde(): Double
}

enum class StatutDeCompte {
    CREATED, SUSPENDED, DELETED, BLOCKED
}
abstract class Operation(
    val montant: Double,
    val date: String = "18/10/2026"
) {
    val numero = ++compteur

    companion object {
        var compteur = 0
    }
}

class Versement(montant: Double) : Operation(montant)

class Retrait(montant: Double) : Operation(montant)

class Banque {
    val comptes = mutableListOf<Compte>()

    fun ajouter(compte: Compte) {
        require(comptes.none { it.code == compte.code })
        comptes.add(compte)
    }

    fun virement(
        source: Compte,
        destination: Compte,
        montant: Double
    ) {
        require(source != destination)

        source.retirer(montant)

        try {
            destination.deposer(montant)
        } catch (e: Exception) {
            source.deposer(montant)
            throw e
        }
    }

    fun totalVersements(compte: Compte) =
        compte.operations.filterIsInstance<Versement>()
            .sumOf { it.montant }

    fun totalRetraits(compte: Compte) =
        compte.operations.filterIsInstance<Retrait>()
            .sumOf { it.montant }
}

fun main() {
    val banque = Banque()

    val c1 = CompteCourant("C123", 1000.0)
    val c2 = CompteEpargne("E456", 2000.0, 0.05)

    banque.ajouter(c1)
    banque.ajouter(c2)

    c1.deposer(500.0)
    c1.retirer(200.0)

    banque.virement(c1, c2, 100.0)

    c2.ajouterInterets()

    println(c1.resume())
    println(c2.resume())

    println("Nombre de comptes : ${banque.comptes.size}")

    println("Comptes à zéro :")
    println(banque.comptes.filter { it.solde == 0.0 })

    println("Solde supérieur à 1000 :")
    println(banque.comptes.filter { it.solde > 1000.0 })

    println("Comptes CREATED :")
    println(
        banque.comptes.filter {
            it.solde > 1000.0 &&
                    it.statut == StatutDeCompte.CREATED
        }
    )
    println("Compte avec le plus de versements :")
    println(
        banque.comptes.maxByOrNull {
                compte -> compte.operations.count { op -> op is Versement }
        }
    )

    println("Comptes courants :")
    println(banque.comptes.filterIsInstance<CompteCourant>())

    println("Total des versements : ${banque.totalVersements(c1)}")
    println("Total des retraits : ${banque.totalRetraits(c1)}")

    println("Compte avec le plus de transactions :")
    println(banque.comptes.maxByOrNull { it.operations.size })
}