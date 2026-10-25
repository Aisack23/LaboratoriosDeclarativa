package org.example;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;

import java.io.InputStream;
import java.math.BigDecimal;

public class Connection {
    private final Prolog engine = new Prolog();

    public Connection() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/calculo.pl")) {
            if (in == null) throw new IllegalStateException("No se encontró calculo.pl");
            engine.setTheory(new Theory(in));
        }
    }

    public String consultar(String predicado, String n1, String n2) throws Exception {
        String consulta = predicado + "(" + aNumeroProlog(n1) + ", " + aNumeroProlog(n2) + ", R).";
        SolveInfo info = engine.solve(consulta);
        if (!info.isSuccess()) {
            return "Sin solución para esa operación";
        }
        String r = info.getTerm("R").toString();
        return r.endsWith(".0") ? r.substring(0, r.length() - 2) : r;
    }

    private static String aNumeroProlog(String texto) {
        String limpio = texto.trim().replace(',', '.');
        String plano;
        try {
            plano = new BigDecimal(limpio).toPlainString();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("'" + texto.trim() + "' no es un número");
        }
        return plano.contains(".") ? plano : plano + ".0";
    }
}