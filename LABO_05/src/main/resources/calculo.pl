% SUMA
sum(N1, N2, R) :-
    number(N1), number(N2),
    R is N1 + N2.

% RESTA
rest(N1, N2, R) :-
    number(N1), number(N2),
    R is N1 - N2.

% MULTIPLICACION
multi(N1, N2, R) :-
    number(N1), number(N2),
    R is N1 * N2.

% DIVISION
divi(N1, N2, R) :-
    number(N1), number(N2),
    N2 =\= 0,
    R is N1 / N2.