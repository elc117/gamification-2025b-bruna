// T6 - Bruna Caetano

// gcc -Wall -o main main.c tela.c game.c -lm -lallegro_font -lallegro_color -lallegro_ttf -lallegro_primitives -lallegro 
#include "tela.h"
#include "game.h"

#include <stdlib.h>
#include <stdio.h>
#include <locale.h>
#include <time.h>
#include <string.h>
#include <stdbool.h>

int main(){
    jogo();
    
    return 0;
}