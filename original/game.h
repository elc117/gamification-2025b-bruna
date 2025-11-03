#ifndef _game_h_  // para evitar problemas coma inclusão múltipla deste arquivo
#define _game_h_

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

// salva o ranking
void salva_top5(void);

// ordena o ranking
void ordena_top5(void);

// pega os dados do arquivo do ranking
void top5(ranking melhores[]);

// cria o array dos circulos
void cria_array_circulos(circulo circulos[]);

// cria os circulos na interface
void cria_circulos_iniciais(void);

// Sorteia as cores
void sorteia(int *vetor);

// desenha o placar
void desenha_top5(void);

// desenha todas as jogadas
void desenha_matriz(void);

// desenha o chute
void desenha_chute(void);

// calcula a pontuacao do jogador 
void pontuacao(void);

// ganhou o jogo
void ganhou(void);

// desistiu do jogo
void desistiu(void);

// conta os pretos e brancos
void conta_pretos_e_brancos();

// verifica se o chute é valido
bool valida(int chute);

//  insere o chute na matriz de chutes
void confirma_chute(void);

// remove uma cor do chute
void remove_ultimo(void);

// adiciona uma cor ao chute
void adiciona_chute(int chute);

// Processa um click na tela
void processa_click(void);

// Inicia a partida, seta as variáveis
void partida(void);

// Inicia o jogo
void jogo(void);
     

#endif