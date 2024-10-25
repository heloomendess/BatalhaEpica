import java.util.Random;
import java.util.Scanner;

public class HeloisaMendes {
    abstract class Inimigo {
        String nome;
        int vida;
        int ataque;
        String fraseApresentacao;
        String fraseMorte;
        int escudo;

        public Inimigo(String nome, int vida, int ataque, String fraseApresentacao, String fraseMorte, int escudo) {
            this.nome = nome;
            this.vida = vida;
            this.ataque = ataque;
            this.fraseApresentacao = fraseApresentacao;
            this.fraseMorte = fraseMorte;
            this.escudo = escudo;
        }

        public void apresentar() {
            System.out.println(fraseApresentacao);
        }

        public void morrer() {
            System.out.println(fraseMorte);
        }

        public int fazAtaque() {
            return ataque;
        }
    }

    class Inimigo1 extends Inimigo {
        public Inimigo1() {
            super("Voldemort", 100, 10, "Eu sou o Voldemort!", "Voldemort foi derrotado!", 5);
        }
    }

    class Inimigo2 extends Inimigo {
        public Inimigo2() {
            super("Darth Veider", 120, 15, "Eu sou o Darth Veider!", "Darth Veider foi derrotado!", 10);
        }
    }

    class Inimigo3 extends Inimigo {
        public Inimigo3() {
            super("Sauron", 150, 20, "Eu sou o Sauron!", "Sauron foi derrotado!", 15);
        }

        @Override
        public int fazAtaque() {
            Random rand = new Random();
            return rand.nextInt(ataque + 1);
        }
    }

    abstract class Arma {
        String nome;
        int dano;

        public Arma(String nome, int dano) {
            this.nome = nome;
            this.dano = dano;
        }

        public abstract int usar();
    }

    class Espada extends Arma {
        public Espada() {
            super("Espada", 15);
        }

        @Override
        public int usar() {
            return dano;
        }
    }

    class Faca extends Arma {
        public Faca() {
            super("Faca", 10);
        }

        @Override
        public int usar() {
            return dano;
        }
    }

    class Pistola extends Arma {
        int municao;

        public Pistola() {
            super("Pistola", 20);
            this.municao = 10;
        }

        @Override
        public int usar() {
            if (municao > 0) {
                municao--;
                return dano;
            } else {
                System.out.println("Pistola sem munição!");
                return 0;
            }
        }
    }

    class Metralhadora extends Arma {
        int municao;

        public Metralhadora() {
            super("Metralhadora", 25);
            this.municao = 30;
        }

        @Override
        public int usar() {
            if (municao > 0) {
                municao--;
                return dano;
            } else {
                System.out.println("Metralhadora sem munição!");
                return 0;
            }
        }
    }

    class Jogador {
        String nome;
        int vida;
        Arma armaCurta;
        Arma armaLonga;

        public Jogador(String nome, int vida, Arma armaCurta, Arma armaLonga) {
            this.nome = nome;
            this.vida = vida;
            this.armaCurta = armaCurta;
            this.armaLonga = armaLonga;
        }

        public void escolherArma() {
            System.out.println("Escolha sua arma de ataque:");
            System.out.println("1. " + armaCurta.nome + " (Dano: " + armaCurta.dano + ")");
            System.out.println("2. " + armaLonga.nome + " (Dano: " + armaLonga.dano + ", Munição: " + ((armaLonga instanceof Pistola) ? ((Pistola) armaLonga).municao : ((Metralhadora) armaLonga).municao) + ")");
        }

        public int atacar(int escolha) {
            if (escolha == 1) {
                return armaCurta.usar();
            } else {
                return armaLonga.usar();
            }
        }
    }

    class Batalha {
        Jogador jogador;
        Inimigo inimigo;
        String vencedor;

        public Batalha(Jogador jogador, Inimigo inimigo) {
            this.jogador = jogador;
            this.inimigo = inimigo;
        }

        public void iniciar() {
            Scanner scanner = new Scanner(System.in);
            inimigo.apresentar();
            while (jogador.vida > 0 && inimigo.vida > 0) {
                jogador.escolherArma();
                int escolha = scanner.nextInt();
                int danoJogador = jogador.atacar(escolha);
                inimigo.vida -= danoJogador;
                System.out.println("Você causou " + danoJogador + " de dano ao " + inimigo.nome);

                if (inimigo.vida > 0) {
                    int danoInimigo = inimigo.fazAtaque();
                    jogador.vida -= danoInimigo;
                    System.out.println(inimigo.nome + " causou " + danoInimigo + " de dano a você");
                }
            }

            if (jogador.vida > 0) {
                vencedor = jogador.nome;
                inimigo.morrer();
            } else {
                vencedor = inimigo.nome;
                System.out.println("Você foi derrotado!");
            }
        }

        public String getVencedor() {
            return vencedor;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite seu nome:");
        String nomeJogador = scanner.nextLine();

        System.out.println("Escolha sua arma de curta distância:");
        System.out.println("1. Espada");
        System.out.println("2. Faca");
        int escolhaCurta = scanner.nextInt();
        HeloisaMendes outerInstance = new HeloisaMendes();
        Arma armaCurta = (escolhaCurta == 1) ? outerInstance.new Espada() : outerInstance.new Faca();

        System.out.println("Escolha sua arma de longa distância:");
        System.out.println("1. Pistola");
        System.out.println("2. Metralhadora");
        int escolhaLonga = scanner.nextInt();
        Arma armaLonga = (escolhaLonga == 1) ? outerInstance.new Pistola() : outerInstance.new Metralhadora();

        Jogador jogador = outerInstance.new Jogador(nomeJogador, 100, armaCurta, armaLonga);

        Inimigo[] inimigos = {outerInstance.new Inimigo1(), outerInstance.new Inimigo2(), outerInstance.new Inimigo3()};
        Batalha[] batalhas = new Batalha[inimigos.length];

        for (int i = 0; i < inimigos.length; i++) {
            jogador.vida = 100;
            batalhas[i] = outerInstance.new Batalha(jogador, inimigos[i]);
            batalhas[i].iniciar();
            if (jogador.vida <= 0) {
                break;
            }
        }

        System.out.println("Resultados das batalhas:");
        for (Batalha batalha : batalhas) {
            if (batalha != null) {
                System.out.println("Vencedor: " + batalha.getVencedor());
            }
        }
    }
}
