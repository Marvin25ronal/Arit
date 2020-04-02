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
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Locale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author marvi
 */
public class Pie implements Instruccion {

    LinkedList<Expresion> exp;
    LinkedList<Expresion> dimensiones;
    int linea, columna;

    public Pie(LinkedList<Expresion> exp, LinkedList<Expresion> dimensiones, int linea, int columna) {
        this.exp = exp;
        this.dimensiones = dimensiones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno e) {
        if (dimensiones.size() > 0) {
            return new Errores(Errores.TipoError.SEMANTICO, "Esta funcion no devuelve nada", linea, columna);
        }
        if (exp.size() == 3) {
            Object val = exp.get(0).getValor(e);
            Object lab = exp.get(1).getValor(e);
            Object tit = exp.get(2).getValor(e);
            if (val instanceof Errores) {
                return val;
            } else if (lab instanceof Errores) {
                return lab;
            } else if (tit instanceof Errores) {
                return tit;
            }
            TipoExp tval = Globales.VarGlobales.getInstance().obtenerTipo(val, e);
            TipoExp tlab = Globales.VarGlobales.getInstance().obtenerTipo(lab, e);
            TipoExp ttit = Globales.VarGlobales.getInstance().obtenerTipo(tit, e);
            if (tval.isVector()) {
                if (tlab.isVector()) {
                    if (ttit.isVector()) {
                        EstructuraLineal vdatos = (EstructuraLineal) val;
                        EstructuraLineal vlab = (EstructuraLineal) lab;
                        EstructuraLineal vtit = (EstructuraLineal) tit;
                        if (vtit.getDimensiones().size() > 1) {
                            return new Errores(Errores.TipoError.SEMANTICO, "el vector de titulo tiene mas de un elemento", linea, columna);
                        } else if (!vtit.getTiposecundario().isString()) {
                            return new Errores(Errores.TipoError.SEMANTICO, "el vector no es de tipo string", linea, columna);
                        }
                        if (!vdatos.getTiposecundario().esNumero()) {
                            return new Errores(Errores.TipoError.SEMANTICO, "El vector de datos tiene que ser de tipo INTEGER o NUMERIC", linea, columna);
                        }
                        String titulo = ((Literal) vtit.getDimensiones().get(0)).getValor().toString();
                        LinkedList<Object> datos = Globales.VarGlobales.getInstance().clonarListaVector(vdatos.getDimensiones(), e);
                        LinkedList<Object> label = Globales.VarGlobales.getInstance().clonarListaVector(vlab.getDimensiones(), e);
                        LinkedList<Double> valores = ObtenerValores(datos, e);
                        LinkedList<String> labels = ObtenerLabels(label, e);
                        if (valores.size() != labels.size()) {
                            CuadrarListas(valores, labels);
                        }

                        HacerGrafica(valores, labels, titulo);

                    } else if (ttit.isString()) {
                        EstructuraLineal vdatos = (EstructuraLineal) val;
                        EstructuraLineal vlab = (EstructuraLineal) lab;
                        if (!vdatos.getTiposecundario().esNumero()) {
                            return new Errores(Errores.TipoError.SEMANTICO, "El vector de datos tiene que ser de tipo INTEGER o NUMERIC", linea, columna);
                        }
                        String titulo = tit.toString();
                        LinkedList<Object> datos = Globales.VarGlobales.getInstance().clonarListaVector(vdatos.getDimensiones(), e);
                        LinkedList<Object> label = Globales.VarGlobales.getInstance().clonarListaVector(vlab.getDimensiones(), e);
                        LinkedList<Double> valores = ObtenerValores(datos, e);
                        LinkedList<String> labels = ObtenerLabels(label, e);
                        if (valores.size() != labels.size()) {
                            CuadrarListas(valores, labels);
                        }

                        HacerGrafica(valores, labels, titulo);
                    } else {
                        return new Errores(Errores.TipoError.SEMANTICO, "El tercer parametro no es de tipo STRING", linea, columna);
                    }
                } else {
                    return new Errores(Errores.TipoError.SEMANTICO, "El segundo vector no es de tipo VECTOR", linea, columna);
                }
            } else {
                return new Errores(Errores.TipoError.SEMANTICO, "El primer parametro no es de tipo VECTOR", linea, columna);
            }
        } else {
            return new Errores(Errores.TipoError.SEMANTICO, "La grafica de PIE no cuenta con la cantidad de parametros necesarios", linea, columna);
        }

        return null;
    }

    private void HacerGrafica(LinkedList<Double> datos, LinkedList<String> labels, String titulo) {
        DefaultPieDataset ds = new DefaultPieDataset();
        for (int i = 0; i < datos.size(); i++) {
            ds.setValue(labels.get(i), datos.get(i));
        }
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                " {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%")
        );
        PieDataset dataset = ds;
        JFreeChart jf = ChartFactory.createPieChart3D(titulo, dataset, true, true, Locale.ITALY);
        ((PiePlot) jf.getPlot()).setLabelGenerator(labelGenerator);
        ChartFrame f = new ChartFrame(titulo, jf);
        f.setSize(1000, 600);
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

    private LinkedList<String> ObtenerLabels(LinkedList<Object> v, Entorno e) {
        LinkedList<String> nueva = new LinkedList<>();
        for (int i = 0; i < v.size(); i++) {
            Literal l = (Literal) v.get(i);
            nueva.add(l.getValor(e).toString());
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

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }

    @Override
    public String toDot(int padre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
