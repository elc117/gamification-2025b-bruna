// T5 - Bruna Caetano

#include <stdio.h>
#include <stdlib.h>
#include <locale.h>
#include <time.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

#define MAX 9
char anteriores[][5] = {"", "", "", "", "", "", "", ""};
int top = 0;

void cor_fundo(int vermelho, int verde, int azul) {
    printf("\e[48;2;%d;%d;%dm", vermelho, verde, azul);
}

void cor_normal(void) {
    printf("\e[m");
}

void espaco_vazio(int quantidade) {
    for (int i = 0; i < quantidade; i++)
        printf(" ");
}

void linha_brancos(int qntd){
    for (int i = 0; i < qntd; i++) {
        cor_fundo(255, 255, 255);
        espaco_vazio(5);
        cor_normal();
        espaco_vazio(1);
    }
}

void linha_pretos(int qntd){
    for (int i = 0; i < qntd; i++) {
        cor_fundo(46, 46, 46);
        espaco_vazio(5);
        cor_normal();
        espaco_vazio(1);
    }
}

void linha_chute(char chute[], int linha){
    char* letras[] = {"v", "l", "a", "e", "z", "n", "i"};
    int cores[7][3] = {{255, 0, 0},{255, 127, 0},{255, 255, 0},{0, 255, 0},{0, 0, 255},{75, 0, 130},{143, 0, 255}};

    for (int i = 0; i < 4; i++) {
        for (int k = 0; k < 7; k++){
            if (tolower(chute[i]) == tolower(*letras[k])){
                cor_fundo(cores[k][0], cores[k][1], cores[k][2]);
            }
        }
        if (linha == 1) {
            espaco_vazio(2);
            printf("%c", chute[i]);
            espaco_vazio(2);
        } 
        else {
            espaco_vazio(5);
        }
        cor_normal();
        espaco_vazio(1);
    }
}

void desenha(char chute[], int pretos, int brancos){
    printf("\n");
    
    for (int j = 0; j < 3; j++) {
        linha_chute(chute, j);
        linha_pretos(pretos);
        linha_brancos(brancos);
        printf("\n");
    }
    printf("\n");

}

int verifica(char chute[], char cores[5]){
    int result = 0;

    for (int i = 0; i < 4; i++){
        for (int j = 0; j < 4; j++){
            if (tolower(chute[i]) == cores[j] && i != j) result++;
            if (tolower(chute[i]) == cores[j] && i == j) result += 10;
        }
    }
    
    return result;
}

bool valida(char chute[]){
    char *letras[] = {"v", "l", "a", "e", "z", "n", "i"};
    int controle = 0, k = 0, igual;

    if (strlen(chute) != 4) return false;

    while(k < 4) { 
        igual = 0;
        for(int j = 0; j < k; j++)
            if(chute[j] == chute[k]) igual = 1; 

        if(igual == 1) return false;
        k++;
    } 

    for (int i = 0; i < 4; i++)
        for (int j = 0; j < 7; j++)
            if (tolower(chute[i]) == tolower(*letras[j])) 
                controle++;

    if (controle == 4) return true;

    return false;
}

void push(char *chute){
    if(top != MAX){
        strcpy(anteriores[top], chute);
        top++;  
    }
}

void processa(char chute[], char cores[5], bool *acertou){
    if(valida(chute)){
        push(chute);
        char temp[5];
        int result = verifica(chute, cores);
        int pretos = result / 10;
        int brancos = result - pretos * 10;

        desenha(chute, pretos, brancos);
        strncpy(temp, cores, 4);

        if (strcmp(chute, temp) == 0) {
            puts("Parabéns! Você acertou!");
            *acertou = true;
        }
    } else {
        puts("Seu chute foi inválido, tente novamente.\n");
    }
}

void desistir(bool *desistiu) {
    *desistiu = true;
    puts("Certo! Foi bom o jogo.");
}

void historico() {
    printf("\n");
    puts("Histórico de jogadas");
    int i = 0;
    while (i < top){
        for (int j = 0; j < 4; j++)
            printf("%c", anteriores[i][j]);
        printf("\n");
        i++;
    }
    printf("\n");
}

void tabela() {
    char* letras[] = {"v", "l", "a", "e", "z", "n", "i"};
    int cores[7][3] = {{255, 0, 0},{255, 127, 0},{255, 255, 0},{0, 255, 0},{0, 0, 255},{75, 0, 130},{143, 0, 255}};

    for (int j = 0; j < 3; j++) {
        for (int i = 0; i < 7; i++) {
            cor_fundo(cores[i][0], cores[i][1], cores[i][2]);
            if (j == 1) {
                espaco_vazio(2);
                printf("%s", letras[i]);
                espaco_vazio(2);
            } 
            else {
                espaco_vazio(5);
            }
            cor_normal();
            espaco_vazio(1);
        }
        printf("\n"); 
    }
}
 
int especial(char chute[]) {
    if (strcmp(chute, "?") == 0) return 1;
    else if (strcmp(chute, "!") == 0) return 2;
    else if (strcmp(chute, "#") == 0) return 3;

    return 0;
}

void sorteia(char *letras[], char *resultado) {
    int vet[5], i = 0, igual;
    char tmp[5];
    srand(time(NULL));

    do { 
        vet[i] = rand() % 6; 
        igual = 0;
        for(int j = 0; j < i; j++){ 
            if(vet[j] == vet[i]) igual = 1; 
        }

        if(igual == 0) i++;
    } 
    while(i < 4);

    for (i = 0; i < 4; i++) {
        strcat(resultado, letras[vet[i]]);
    }
}

void partida() { // cores = {"vermelho", "laranja", "amarelo", "verde", "azul", "anil", "violeta"}
    char chute[5], cores[5] = {};
    char* letras[] = {"v", "l", "a", "e", "z", "n", "i"};
    int chances = 0;
    bool desistiu = false, acertou = false;

    sorteia(letras, cores);
    do {
        printf("\nDigite seu chute: \n? - tabela de cores\n! - historico de jogadas\n# - desistir de jogar\n- ");
        scanf("%s", chute);
        int tipo = especial(chute);
        if (tipo == 1) tabela();
        else if (tipo == 2) historico();
        else if (tipo == 3) desistir(&desistiu);
        else {
            processa(chute, cores, &acertou);
            chances++;
        }
        if (acertou) break;
        if (desistiu) break;
    } 
    while (chances < 9);
}

void jogo() { // inicia o jogo
    char resposta[1];
    puts("\n---- Mastermind ----");

    while (strcmp(resposta, "N") != 0) {
        partida();
        puts("Deseja jogar outra vez?");
        puts("Digite qualquer coisa para sim e 'N' para não.");
        scanf("%s", resposta);
        if (strcmp(resposta, "n") == 0) break;
    }

    puts("Até logo!");
}

int main() {
    setlocale(LC_ALL, "Portuguese");
    jogo();

    return 0;
}