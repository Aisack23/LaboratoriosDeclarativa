sumarnsimo(1, 1).
sumarnsimo(N, R) :-
    N > 1,
    N1 is N - 1,
    sumarnsimo(N1, R1),
    R is N + R1.













