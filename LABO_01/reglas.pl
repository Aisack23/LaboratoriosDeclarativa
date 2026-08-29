
:- consult('hechos.pl').

arma_de_fuego(Personaje) :-
    arma(Personaje, pistola).

edad_joven(Personaje) :-
    edad(Personaje, Edad),
    Edad >= 20,
    Edad < 30.

preparado_para_zona(Personaje, Lugar) :-
    encuentra_en(Personaje, Lugar),
    dificultad(Lugar, alta),
    arma(Personaje, _).

amenaza_plagas(Lugar) :-
    aparece_en(ganados, Lugar);
    aparece_en(regeneradores, Lugar).
