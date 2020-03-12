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
import java.util.LinkedList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author marvi
 */
public class BarPlot implements Instruccion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public BarPlot(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
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
            Object objH = exp.get(0).getValor(e);
            Object objXlab = exp.get(1).getValor(e);
            Object objYlab = exp.get(2).getValor(e);
            Object objMain = exp.get(3).getValor(e);
            Object objNames = exp.get(4).getValor(e);
            if (objH instanceof Errores) {
                return objH;
            } else if (objXlab instanceof Errores) {
                return objXlab;
            } else if (objYlab instanceof Errores) {
                return objYlab;
            } else if (objMain instanceof Errores) {
                return objMain;
            } else if (objNames instanceof Errores) {
                return objNames;
            }
            String Xlabel, Ylabel, Titulo;
            LinkedList<String> Nombres = null;
            LinkedList<Double> H = null;
            TipoExp tH = Globales.VarGlobales.getInstance().obtenerTipo(objH, e);
            TipoExp tXlab = Globales.VarGlobales.getInstance().obtenerTipo(objXlab, e);
            TipoExp tYlab = Globales.VarGlobales.getInstance().obtenerTipo(objYlab, e);
            TipoExp tMain = Globales.VarGlobales.getInstance().obtenerTipo(objMain, e);
            TipoExp tName = Globales.VarGlobales.getInstance().obtenerTipo(objNames, e);
            if (tXlab.isVector()) {
                EstructuraLineal aux = (EstructuraLineal) objXlab;
                if (aux.getDimensiones().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Xlab tiene mas de un elemento", linea, columna);
                } else if (!aux.getTiposecundario().isString()) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Xlab no es de tipo STRING", linea, columna);
                }
                Xlabel = ((Literal) aux.getDimensiones().get(0)).getValor().toString();
            } else if (tXlab.isString()) {
                Xlabel = objXlab.toString();
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Xlab tiene que ser de tipo STRING", linea, columna);
            }
            if (tYlab.isVector()) {
                EstructuraLineal aux = (EstructuraLineal) objYlab;
                if (aux.getDimensiones().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Ylab tiene mas de un elemento", linea, columna);
                } else if (!aux.getTiposecundario().isString()) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Ylab no es de tipo STRING", linea, columna);
                }
                Ylabel = ((Literal) aux.getDimensiones().get(0)).getValor().toString();
            } else if (tYlab.isString()) {
                Ylabel = objYlab.toString();
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Ylab tiene que ser de tipo STRING", linea, columna);
            }
            if (tMain.isVector()) {
                EstructuraLineal aux = (EstructuraLineal) objMain;
                if (aux.getDimensiones().size() > 1) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Main tiene mas de un elemento", linea, columna);
                } else if (!aux.getTiposecundario().isString()) {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable Main no es de tipo STRING", linea, columna);
                }
                Titulo = ((Literal) aux.getDimensiones().get(0)).getValor().toString();
            } else if (tMain.isString()) {
                Titulo = objMain.toString();
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Main tiene que ser de tipo STRING", linea, columna);
            }
            if (tName.isVector()) {
                EstructuraLineal aux = (EstructuraLineal) objNames;
                if (!aux.getTiposecundario().isString()) {
                    return new Errores(Errores.TipoError.SEMANTICO, "El vector de Name no es de tipo STRING", linea, columna);
                }
                Nombres = ObtenerLabels(aux.getDimensiones(), e);
                if (tH.isVector()) {
                    EstructuraLineal auxH = (EstructuraLineal) objH;
                    if (!auxH.getTiposecundario().esNumero()) {
                        return new Errores(Errores.TipoError.SEMANTICO, "El vector H no es de tipo NUMERIC o INTEGER", linea, columna);
                    }
                    H = ObtenerValores(auxH.getDimensiones(), e);

                    if (H.size() != Nombres.size()) {
                        CuadrarListas(H, Nombres);
                    }
                    Construir(H, Nombres, Xlabel, Ylabel, Titulo);

                } /*else if (tH.isMatrix()) {
                    Matrix m = (Matrix) objH;
                    if (!m.getTiposecundario().esNumero()) {

                        return new Errores(Errores.TipoError.SEMANTICO, "La matriz tiene que tener elementos de tipo NUMERIC o INTEGER", linea, columna);
                    }
                    H = ObtenerValores(m, e);

                    if (H.size() != Nombres.size()) {
                        CuadrarListas(H, Nombres);
                    }
                    Construir(H, Nombres, Xlabel, Ylabel, Titulo);
                } */else {
                    return new Errores(Errores.TipoError.SEMANTICO, "La variable H no es de tipo VECTOR", linea, columna);
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "La variable Names no es de tipo VECTOR", linea, columna);
            }

        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "Cantidad de parametros incorrecta", linea, columna);
        }
        return null;
    }

    private void Construir(LinkedList<Double> datos, LinkedList<String> nombres, String X, String Y, String titulo) {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        for (int i = 0; i < datos.size(); i++) {
            ds.addValue(datos.get(i), nombres.get(i), nombres.get(i));
        }
        CategoryDataset data = ds;
        JFreeChart js = ChartFactory.createBarChart3D(titulo, X, Y, data, PlotOrientation.VERTICAL, true, true, false);
        ChartFrame f = new ChartFrame(titulo, js);
        CategoryPlot plot = (CategoryPlot) js.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                        Math.PI / 6.0));
        f.setSize(1500, 1000);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private LinkedList<Double> ObtenerValores(LinkedList<Object> v, Entorno e) {
        LinkedList<Double> nueva = new LinkedList<>();
        for (int i = 0; i < v.size(); i++) {
            Literal l = (Literal) v.get(i);
            nueva.add(Double.parseDouble(l.getValor(e).toString()));
        }
        return nueva;
    }

   

    private void CuadrarListas(LinkedList<Double> a, LinkedList<String> b) {

        if (a.size() > b.size()) {
            int desc = 0;
            for (int i = b.size(); i < a.size(); i++) {
                b.add("Desconocido" + desc);
                desc++;
            }
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "El tamanio de las labels no tiene la misma longitud", linea, columna));
        } else if (b.size() > a.size()) {
            for (int i = a.size(); i < b.size(); i++) {
                a.add(0.0);
            }
            Globales.VarGlobales.getInstance().AgregarEU(new Errores(Errores.TipoError.SEMANTICO, "El tamanio de los datos no tiene la misma longitud", linea, columna));
        }
    }

    private LinkedList<String> ObtenerLabels(LinkedList<Object> v, Entorno e) {
        LinkedList<String> nueva = new LinkedList<>();
        for (int i = 0; i < v.size(); i++) {
            Literal l = (Literal) v.get(i);
            nueva.add(l.getValor(e).toString());
        }
        return nueva;
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
