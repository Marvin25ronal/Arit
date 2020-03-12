/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Entorno.Entorno;
import Expresion.Expresion;
import Expresion.Literal;
import Expresion.TipoExp;
import Instruccion.Instruccion;
import Objetos.EstructuraLineal;
import Objetos.Matrix;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.LinkedList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author marvi
 */
public class Plot implements Instruccion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public Plot(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        if (dimensiones.size() > 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "Este objeto no retorna nada ", linea, columna);
        }
        if (exp.size() == 5) {
            Object def = exp.get(4).getValor(e);
            if (def instanceof Errores) {
                return def;
            }
            TipoExp tdef = Globales.VarGlobales.getInstance().obtenerTipo(def, e);
            if (tdef.isVector()) {
                EstructuraLineal t = (EstructuraLineal) def;
                if (t.getTiposecundario().isString()) {
                    if (t.getDimensiones().size() > 1) {
                        return new Errores(Errores.TipoError.SEMANTICO, "El ultimo parametro tiene mas d eun elemento", linea, columna);
                    }
                    Literal aux = (Literal) t.getDimensiones().get(0);
                    exp.set(4, aux);
                    return new LinePlot(exp, dimensiones, linea, columna);

                } else if (t.getTiposecundario().esNumero()) {
                    if (t.getDimensiones().size() != 2) {
                        return new Errores(Errores.TipoError.SEMANTICO, "El ultimo parametro no cumple con 2 elementos", linea, columna);
                    }

                    return new Dispersion(exp, dimensiones, linea, columna, t).ejecutar(e);
                }
            } else if (tdef.isString()) {
                Literal aux = new Literal(def, tdef, linea, columna);
                exp.set(4, aux);
                return new LinePlot(exp, dimensiones, linea, columna).ejecutar(e);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "Cantidad de parametros incorrectas", linea, columna);
        }
        return null;
    }

    @Override
    public int linea() {
        return this.linea;
    }

    @Override
    public int columna() {
        return this.columna;
    }

}
