package Services;
import Models.Catalogue;
import Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;


public interface CatalogueService {

    Catalogue Save(Catalogue c);
}
