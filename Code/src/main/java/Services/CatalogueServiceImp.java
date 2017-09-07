package Services;

import Models.Catalogue;
import org.springframework.stereotype.Service;
import Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CatalogueServiceImp implements CatalogueService {
    @Autowired
    Repo object;


    @Override
    public Catalogue Save(Catalogue c){
        try{
            object.save(c);
        }
        catch (Exception e){

        }
        return c;

    }
}
