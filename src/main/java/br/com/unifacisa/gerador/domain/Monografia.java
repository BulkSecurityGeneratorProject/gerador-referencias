package br.com.unifacisa.gerador.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Monografia.
 */

@Document(collection = "monografia")
public class Monografia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("titulo")
    private String titulo;

    @Field("subtitulo")
    private String subtitulo;

    @NotNull
    @Field("edicao")
    private String edicao;

    @NotNull
    @Field("local")
    private String local;

    @NotNull
    @Field("editora")
    private String editora;

    @NotNull
    @Field("data_publicacao")
    private String dataPublicacao;

    @Field("paginas")
    private String paginas;

    @Field("volumes")
    private String volumes;

    @Field("user_id")
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Monografia titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public Monografia subtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
        return this;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getEdicao() {
        return edicao;
    }

    public Monografia edicao(String edicao) {
        this.edicao = edicao;
        return this;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getLocal() {
        return local;
    }

    public Monografia local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getEditora() {
        return editora;
    }

    public Monografia editora(String editora) {
        this.editora = editora;
        return this;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public Monografia dataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getPaginas() {
        return paginas;
    }

    public Monografia paginas(String paginas) {
        this.paginas = paginas;
        return this;
    }

    public void setPaginas(String paginas) {
        this.paginas = paginas;
    }

    public String getVolumes() {
        return volumes;
    }

    public Monografia volumes(String volumes) {
        this.volumes = volumes;
        return this;
    }

    public void setVolumes(String volumes) {
        this.volumes = volumes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Monografia monografia = (Monografia) o;
        if (monografia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monografia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Monografia{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", subtitulo='" + getSubtitulo() + "'" +
            ", edicao='" + getEdicao() + "'" +
            ", local='" + getLocal() + "'" +
            ", editora='" + getEditora() + "'" +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            ", paginas='" + getPaginas() + "'" +
            ", volumes='" + getVolumes() + "'" +
            "}";
    }
}
