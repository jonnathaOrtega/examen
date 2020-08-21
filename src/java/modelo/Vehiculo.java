/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CRISTINA CALLE
 */
@Entity
@Table(name = "vehiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v")
    , @NamedQuery(name = "Vehiculo.findByA\u00f1o", query = "SELECT v FROM Vehiculo v WHERE v.vehiculoPK.a\u00f1o = :a\u00f1o")
    , @NamedQuery(name = "Vehiculo.findByModelo", query = "SELECT v FROM Vehiculo v WHERE v.modelo = :modelo")
    , @NamedQuery(name = "Vehiculo.findByMarca", query = "SELECT v FROM Vehiculo v WHERE v.marca = :marca")
    , @NamedQuery(name = "Vehiculo.findByCilindraje", query = "SELECT v FROM Vehiculo v WHERE v.cilindraje = :cilindraje")
    , @NamedQuery(name = "Vehiculo.findByPaisF", query = "SELECT v FROM Vehiculo v WHERE v.paisF = :paisF")
    , @NamedQuery(name = "Vehiculo.findByPlaca", query = "SELECT v FROM Vehiculo v WHERE v.vehiculoPK.placa = :placa")})
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VehiculoPK vehiculoPK;
    @Size(max = 45)
    @Column(name = "modelo")
    private String modelo;
    @Size(max = 45)
    @Column(name = "marca")
    private String marca;
    @Column(name = "cilindraje")
    private Integer cilindraje;
    @Size(max = 45)
    @Column(name = "paisF")
    private String paisF;

    public Vehiculo() {
    }

    public Vehiculo(VehiculoPK vehiculoPK) {
        this.vehiculoPK = vehiculoPK;
    }

    public Vehiculo(int año, String placa) {
        this.vehiculoPK = new VehiculoPK(año, placa);
    }

    public VehiculoPK getVehiculoPK() {
        return vehiculoPK;
    }

    public void setVehiculoPK(VehiculoPK vehiculoPK) {
        this.vehiculoPK = vehiculoPK;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(Integer cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getPaisF() {
        return paisF;
    }

    public void setPaisF(String paisF) {
        this.paisF = paisF;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vehiculoPK != null ? vehiculoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.vehiculoPK == null && other.vehiculoPK != null) || (this.vehiculoPK != null && !this.vehiculoPK.equals(other.vehiculoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Vehiculo[ vehiculoPK=" + vehiculoPK + " ]";
    }
    
}
