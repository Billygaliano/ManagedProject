/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author inftel23
 */
@Entity
@Table(name = "ATTACHMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachment.findAll", query = "SELECT a FROM Attachment a"),
    @NamedQuery(name = "Attachment.findByIdAttachment", query = "SELECT a FROM Attachment a WHERE a.idAttachment = :idAttachment"),
    @NamedQuery(name = "Attachment.findByBlob", query = "SELECT a FROM Attachment a WHERE a.blob = :blob"),
    @NamedQuery(name = "Attachment.findByNombre", query = "SELECT a FROM Attachment a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Attachment.findByIdProject", query = "SELECT a FROM Attachment a WHERE a.idProject = :idProject")})
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="ATTACHMENT_SEQUENCE") 
    @SequenceGenerator(name="ATTACHMENT_SEQUENCE",sequenceName="at_seq", allocationSize=1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ATTACHMENT")
    private Long idAttachment;
    @Size(max = 20)
    @Column(name = "BLOB")
    private String blob;
    @Size(max = 300)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "ID_PROJECT", referencedColumnName = "ID_PROJECT")
    @ManyToOne(optional = false)
    private Project idProject;

    public Attachment() {
    }

    public Attachment(Long idAttachment) {
        this.idAttachment = idAttachment;
    }

    public Long getIdAttachment() {
        return idAttachment;
    }

    public void setIdAttachment(Long idAttachment) {
        this.idAttachment = idAttachment;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Project getIdProject() {
        return idProject;
    }

    public void setIdProject(Project idProject) {
        this.idProject = idProject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAttachment != null ? idAttachment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachment)) {
            return false;
        }
        Attachment other = (Attachment) object;
        if ((this.idAttachment == null && other.idAttachment != null) || (this.idAttachment != null && !this.idAttachment.equals(other.idAttachment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mgproject.entities.Attachment[ idAttachment=" + idAttachment + " ]";
    }
    
}
