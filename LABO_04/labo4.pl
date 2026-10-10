%LABO 4: almacenar cada digito de un numero en una lista

%casos base
almacenar(N, L):-
    almacenar(N, L, []).

almacenar(0, L, L).

%recurcion
almacenar(N, L, Aux):-
    N > 0,
    Digito is N mod 10,
    Resto is N // 10,
    almacenar(Resto, L, [Digito|Aux]).
