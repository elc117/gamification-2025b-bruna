// inclui as definicoes
#include "tela.h"
#include "game.h"

struct ESTADO {
    int chances;
    int chutes[4][8];
    int acertos[2][8];
    int cores[4];
    bool acertou;
    bool desistiu;
    bool nao;
} estado;

int contador = 0, chuteAtual[4], pontos = 0;
bool maior = false;

circulo circulos[10];
ponto click;
ranking melhores[5];

void salva_top5() {
    FILE *arq = fopen("top5.txt", "w");
    if (arq == NULL) {
        printf("Erro ao abrir o arquivo\n");
        exit(1);
    }

    for (int i = 0; i < 5; i++) {
        fprintf(arq, "%s %d\n", melhores[i].iniciais, melhores[i].pontos);
    }

    fclose(arq);
}

void ordena_top5() {
    for (int i = 0; i < 5; i++) {
        for (int j = i + 1; j < 5; j++) {
            if (melhores[i].pontos < melhores[j].pontos) {
                ranking aux = melhores[i];
                melhores[i] = melhores[j];
                melhores[j] = aux;
            }
        }
    }

}

void top5(ranking melhores[]) {
    FILE *arq = fopen("top5.txt", "r");
    if (arq == NULL) {
        printf("Erro ao abrir o arquivo\n");
        exit(1);
    }

    printf("Melhores jogadores:\n");
    for (int i = 0; i < 5; i++) {
        fscanf(arq, "%s %d", melhores[i].iniciais, &melhores[i].pontos);
        printf("%s %d\n", melhores[i].iniciais, melhores[i].pontos);
    }

    fclose(arq);
}

void cria_array_circulos(circulo circulos[]) {
    for (int i=0; i<10; i++) {
        circulos[i].centro.x = LARGURA - i*50 - 50;
        circulos[i].centro.y = ALTURA - 50;
        circulos[i].raio = 20;
        circulos[i].cor = (i <= 6) ? i : preto;
    }
}

void cria_circulos_iniciais() {
	for (int i = 0; i < 10; i++) {
		tela_circulo(circulos[i].centro.x, circulos[i].centro.y, circulos[i].raio, 2, branco, circulos[i].cor);
	}

    tela_texto(circulos[9].centro.x, circulos[9].centro.y, 20, branco, "⟵");
    tela_texto(circulos[8].centro.x, circulos[8].centro.y, 20, branco, "✓");
    tela_texto(circulos[7].centro.x, circulos[7].centro.y, 20, branco, "✕");
}

void sorteia(int *resultado) {
    int vet[4], i = 0, igual;
    srand(time(NULL));

    do { 
        vet[i] = rand() % 7; 
        igual = 0;
        for (int j = 0; j < i; j++) { 
            if(vet[j] == vet[i]) igual = 1; 
        }

        if(igual == 0) i++;
    } while(i < 4);

    for (i = 0; i < 4; i++) {
        resultado[i] = vet[i];
    }
}

void desenha_top5() {
    tela_texto_esq(LARGURA - 50, 50, 20, branco, "TOP 5");
    for (int i = 0; i < 5; i++) {
        char texto[10];
        sprintf(texto, "%s %d", melhores[i].iniciais, melhores[i].pontos);
        tela_texto_esq(LARGURA - 50, 100 + i * 50, 20, branco, texto);
    }
}

void desenha_matriz() {
    int x = 50, y = 50;
    for (int i = 0; i < estado.chances; i++) {
        for (int j = 0; j < 4; j++) {
            tela_circulo(x, y, 20, 2, branco, estado.chutes[i][j]);
            x += 50;
        }
        for (int j = 0; j < estado.acertos[0][i]; j++) {
            tela_circulo(x, y, 20, 2, branco, preto);
            x += 50;
        }
        for (int j = 0; j < estado.acertos[1][i]; j++) {
            tela_circulo(x, y, 20, 2, branco, branco);
            x += 50;
        }
        
        x = 50;
        y += 50;
    }
}

void desenha_chute() {
    for (int i = 0; i < contador; i++)
        tela_circulo((i + 1) * 50, ALTURA - 50, 20, 2, branco, chuteAtual[i]);
}

void click_botao_salvar() {
    if (maior) {
        if (tela_rato_clicado()) {
            if(tela_rato_x() >= 450 && tela_rato_x() <= 230 && tela_rato_y() >= 490 && tela_rato_y() <= 250){
                ordena_top5();
                salva_top5();
            }
        }
    }
}

void verifica_maior() {
    if (pontos > melhores[4].pontos) {
        maior = true;
    }
}

void pega_iniciais() {
    tela_texto(LARGURA/2, ALTURA/2 + 100, 50, branco, "Digite suas iniciais:");

    for (int i = 0; i < 3; i++) {
        melhores[4].iniciais[i] = tela_tecla();
        tela_texto(LARGURA/2, ALTURA/2 + 150, 50, branco, &melhores[4].iniciais[i]);
    }

    tela_retangulo(450, 230, 490, 250, 5, azul, azul);
	tela_texto(470, 240, 18, branco, "Salvar");
}

void pontuacao() {
    for (int i = 0; i < estado.chances; i++) {
        pontos += estado.acertos[0][i] * 5;
        pontos += estado.acertos[1][i] * 3;
    }

    if(estado.acertou) pontos *= (9 - estado.chances);

    char str[100];
    sprintf(str, "%s %d", "Pontuação: ", pontos);

    tela_texto(LARGURA/2, ALTURA/2 + 50, 50, branco, str);
    printf("Pontuação: %d\n", pontos);
    verifica_maior();

    if (maior == true) {
        pega_iniciais();
        melhores[4].pontos = pontos;
    }
}

void perdeu() {
    pontuacao();

    tela_texto(LARGURA/2, ALTURA/2, 50, branco, "Você perdeu!");
    tela_inicial();
}

void ganhou() {
    pontuacao();

    tela_texto(LARGURA/2, ALTURA/2, 50, branco, "Você ganhou!");
    tela_inicial();

    click_botao_salvar();
}

void desistiu() {
    pontuacao();

    tela_texto(LARGURA/2, ALTURA/2, 50, branco, "Você desistiu!");
    tela_inicial();

    click_botao_salvar();
}

void conta_pretos_brancos() {
    int pretos = 0, brancos = 0;

    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            if (chuteAtual[i] == estado.cores[j] && i == j) pretos++;
            if (chuteAtual[i] == estado.cores[j] && i != j) brancos++;
        }
    }

    if (pretos == 4) {
        estado.acertou = true;
        printf("Acertou\n");
    }

    estado.acertos[0][estado.chances] = pretos;
    estado.acertos[1][estado.chances] = brancos;
}

bool valida(int chute) {
    for (int i = 0; i < 4; i++) {
        if (chuteAtual[i] == chute) return false;
    }

    return true;
}

void confirma_chute() {
    printf("chute atual: %d %d %d %d\n", chuteAtual[0], chuteAtual[1], chuteAtual[2], chuteAtual[3]);

    conta_pretos_brancos();
    for (int i = 0; i < 4; i++) {
        estado.chutes[estado.chances][i] = chuteAtual[i];
        chuteAtual[i] = preto;
    }

    contador = 0;
    estado.chances++;
    desenha_matriz();
}

void remove_ultimo() {
    contador--;

    if (contador < 0) return;

    chuteAtual[contador] = preto;
}

void adiciona_chute(int chute) {
    if (contador >= 4) return;
    if (!valida(chute)) return;
    
    chuteAtual[contador] = chute;
    contador++;

    desenha_chute();
}

void processa_click() {
    click.x = tela_rato_x();
    click.y = tela_rato_y();

    int tmp = circulo_no_ponto(10, circulos, click);

    if (tmp == 7) {
        estado.desistiu = true;
        printf("Desistiu\n");
    } else if (tmp == 8) {
        printf("Confirmou\n");
        confirma_chute();
    } else if (tmp == 9) {
        printf("Apagou\n");
        remove_ultimo();
    } else if (tmp >= 0 && tmp < 7) {
        printf("Chute: %d\n", tmp);
        adiciona_chute(tmp);
    }
}

void partida() {
    estado.chances = 0;
    estado.acertou = false;
    estado.desistiu = false;
    estado.nao = false; 
    sorteia(estado.cores);
    cria_array_circulos(circulos);
    top5(melhores);
    pontos = 0;

    printf("Cores: %d %d %d %d\n", estado.cores[0], estado.cores[1], estado.cores[2], estado.cores[3]);
    do {
        if (tela_rato_clicado()) {
            processa_click();
        }

        if (estado.desistiu) {
            desistiu();
            break;
        } else if (estado.acertou) {
            ganhou();
            break;
        }

        tela_jogo();
    } while (estado.chances < 9);

    tela_atualiza();
}

void jogo() {
    tela_inicio(LARGURA, ALTURA, "Jogo Mastermind");

    do {
        tela_inicial();

        if (tela_rato_apertado()) {
            if(tela_rato_x() >= 450 && tela_rato_x() <= 490 && tela_rato_y() >= 230 && tela_rato_y() <= 250){
                partida();
            } else if(tela_rato_x() >= 500 && tela_rato_x() <= 540 && tela_rato_y() >= 230 && tela_rato_y() <= 250){
                estado.nao = true;
            }
        }
    } while (estado.nao == false);

    tela_fim();
}
