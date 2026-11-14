#lang racket

; FUNCION PARA CALCULAR EL INTERES DE UN DEPOSITO
(define (calcular_interes deposito)
  (cond
    [(<= deposito 40000) (* deposito 0.035)]
    [(<= deposito 260000) (* deposito 0.04)]
    [else (* deposito 0.045)]
  )
)

(display "Ingrese la cantidad de dinero a depositar: $")
(define deposito (read))

; VALIDAR Y CALCULAR EL INTERES
(if (and (number? deposito) (>= deposito 0))
    (begin
      (display "El interes generado es: $")
      (displayln (calcular_interes deposito))
    )
    (displayln "Error: Ingrese una cantidad valida.")
)

