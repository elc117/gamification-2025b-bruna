#ifndef _tela_h_  // para evitar problemas coma inclusão múltipla deste arquivo
#define _tela_h_

//
// tela.h
// ------
//
// funções de acesso à tela
//

#include <stdlib.h>
#include <stdio.h>
#include <locale.h>
#include <time.h>
#include <string.h>
#include <stdbool.h>
#include <math.h>

// Os includes do allegro
#include <allegro5/allegro.h>
#include <allegro5/allegro_primitives.h>
#include <allegro5/allegro_font.h>
#include <allegro5/allegro_ttf.h>

// funcao para sair do programa em caso de erro
void cai_fora(char *msg);

// inicializa o teclado
void tela_inicializa_teclado(void);

// inicializa a janela
void tela_inicializa_janela(float l, float a, char n[]);

// valores para representar cores pré-definidas
#define azul 0
#define vermelho 1
#define verde 2
#define amarelo 3
#define laranja 4
#define rosa 5
#define marrom 6
#define preto 7
#define branco 8
#define cinza 9

// inicializa as cores
void tela_inicializa_cores(void);

// cria uma nova cor
// vm, az, vd componentes vermelho, azul e verde (entre 0 e 1) da nova cor
// retorna um valor inteiro que deve ser usado nas funções de desenho
int tela_cria_cor(float vm, float az, float vd);

// inicialização da tela
// cria uma janela com o tamanho dado em pixels
// deve ser executada antes do uso de qualquer outra função da tela
void tela_inicio(int largura, int altura, char nome[]);

// finalização da tela
// deve ser chamada no final da utilização da tela, nenhuma outra função da
// tela deve ser chamada depois desta.
void tela_fim(void);

// atualiza a tela
// faz com o que foi desenhado na tela desde a última atualização
// realmente apareça.
// caso necessário, espera até que a imagem anterior tenha ficado
// na tela pelo tempo adequado.
// antes da chamada a esta função a imagem anterior fica sendo exibida, 
// o conteúdo da nova imagem fica só na memória.
void tela_atualiza(void);

// frequencia de atualizacao da tela
#define QUADROS_POR_SEGUNDO 30.0
#define SEGUNDOS_POR_QUADRO (1/QUADROS_POR_SEGUNDO)

// DESENHO

// desenha um círculo
// x e y são as coordenadas do centro do círculo, r é o raio
// l é a largura da linha, corl a cor da linha e corint é a cor
// do interior do círculo
void tela_circulo(float x, float y, float r, float l, int corl, int corint);

// desenha uma linha reta
// x1, y1 e x2, y2 são as coordenadas das pontas, l a largura e corl a cor
void tela_linha(float x1, float y1, float x2, float y2, float l, int corl);

// desenha um retangulo
// x1, y1 são as coordenadas do canto superior esquerdo, 
// x2, y2 as do inferior direito
// l é a largura da linha, corl a cor da linha e corint é a cor
// do interior do retangulo
void tela_retangulo(float x1, float y1, float x2, float y2, float l,
                    int corl, int corint);

// configura fontes
void tela_prepara_fonte(int tam);

// desenha texto centrado
// x,y coordenadas do meio do texto, tam tamanho das letras, c cor, t texto
void tela_texto(float x, float y, int tam, int c, char t[]);

// desenha texto à esquerda
// x,y coordenadas do fim do texto, tam tamanho das letras, c cor, t texto
void tela_texto_esq(float x, float y, int tam, int c, char t[]);

// x, y coordenadas do inicio do texto, tam tamanho das letras, c cor, t texto
void tela_texto_dir(float x, float y, int tam, int c, char t[]);

// TEMPO
// retorna quantos segundos transcorreram desde o início do programa
double relogio(void);

#define ALTURA 700
#define LARGURA 1000

typedef struct {
    float x, y;
} ponto;

typedef struct {
    ponto centro;
    float raio;
    int cor;
} circulo;

typedef struct {
    int pontos;
    char iniciais[4];
} ranking;

// ENTRADA DE DADOS
// retorna a posição x do mouse
float tela_rato_x(void);

// retorna a posição y do mouse
float tela_rato_y(void);

// retorna se o botão do mouse está apertado
bool tela_rato_apertado(void);

// retorna se o botão do mouse foi clicado (apertado e solto) desde a última
// chamada a esta função
bool tela_rato_clicado(void);

// retorna a posição x do mouse na última vez que ele foi clicado
float tela_rato_x_clique(void);

// retorna a posição y do mouse na última vez que ele foi clicado
float tela_rato_y_clique(void);

// retorna uma tecla digitada
// pode retornar um caractere imprimível ou '\b' para backspace ou '\n' para
// enter ou '\0' para qualquer outra coisa ou se não tiver sido digitado nada.
char tela_tecla(void);


// retorna se o ponto está dentro do círculo
bool ponto_no_circulo(ponto p, circulo c);

// retorna o índice do circulo do vetor no qual está o ponto, ou -1 se não tiver em nenhum deles
int circulo_no_ponto(int n, circulo circulos[n], ponto p);

// Desenha uma bolinha no lugar do mouse
void desenha_mouse(void);

// Desenha a tela durante o jogo
void tela_jogo(void);

// Desenha a tela de inicio do jogo
void tela_inicial(void);

// Desenha a tela durante a partida
void tela_jogo(void);

// Desenha a tela de fim de jogo
void tela_final(void);

#endif 