package Models;
import java.io.Serializable;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "Catalogue_table", schema = "public")

public class Catalogue implements Serializable {

    private static final long serialVersionUID = 6637087385433228063L;

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", unique=true, nullable = false)
    public Long Id;

    @Column(name = "Name", nullable = false)
    public String Name;

    @Column(name = "Description")
    public String Description;

    @Column(name = "Image")
    public String ImagePath;

    @Column(name = "Price")
    public String Price;

    public Catalogue(){

    }

    public Catalogue(String name, String description, String imagePath, String price) {
        this.Name = name;
        this.Description = description;
        this.ImagePath = imagePath;
        this.Price = price;
    }

    public Catalogue(Long Id, String name, String description, String imagePath, String price) {
        this.Id = Id;
        this.Name = name;
        this.Description = description;
        this.ImagePath = imagePath;
        this.Price = price;
    }

/*
    public Long getLong() {
        return Id;
    }

    public void setLong(Long aLong) {
        this.Id = aLong;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        this.ImagePath = imagePath;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }*/

    @Override
    public int hashCode() {
        return Id ==  null? 0: Id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (Id == null || obj == null || getClass() != obj.getClass())
            return false;
        Catalogue toCompare = (Catalogue) obj;
        return Id.equals(toCompare.Id);
    }

    @Override
    public String toString() {
        return "Catalogue[" +
                "Id=" + Id +
                ", Name='" + Name +
                ", Description='" + Description +
                ", ImagePath='" + ImagePath +
                ", Price='" + Price  +
                ']';
    }
}







