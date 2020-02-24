/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entorno;

import Expresion.TipoExp;

/**
 *
 * @author marvi
 */
public abstract class Simbolo {

    private TipoExp tipo;
    private TipoExp tiposecundario;

    public Simbolo(TipoExp tipo, TipoExp tiposecundario) {
        this.tipo = tipo;
        this.tiposecundario = tiposecundario;
    }

    /**
     * @return the tipo
     */
    public TipoExp getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoExp tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the tiposecundario
     */
    public TipoExp getTiposecundario() {
        return tiposecundario;
    }

    /**
     * @param tiposecundario the tiposecundario to set
     */
    public void setTiposecundario(TipoExp tiposecundario) {
        this.tiposecundario = tiposecundario;
    }

}
