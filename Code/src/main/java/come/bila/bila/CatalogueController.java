package come.bila.bila;

import Models.Catalogue;
import Repository.Repo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import Services.CatalogueService;
import Services.CatalogueServiceImp;

import java.io.Console;
import java.util.List;

@RequestMapping("catalogueApi")
@RestController

public class CatalogueController {

    CatalogueService catObject = new CatalogueService() {
        Repo object;
        @Override
        public Catalogue Save(Catalogue c) {
            System.out.println("In save function");
            Long thisId = new Long(1);
            Catalogue obj = new Catalogue(thisId,"name","name","name","name");
            try{
                object.save(obj);
                System.out.println("save method executed");
            }
            catch (Exception e){
                System.out.println("save exception");
                System.out.println(e.getLocalizedMessage());
            }
            return c;
        }
    };
    //@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})

    @RequestMapping("method2")
    public String newMethod() {
        return "You have reached the controller new method";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Catalogue> addCatalogue() {
        Long thisId = new Long(1);
        Catalogue obj = new Catalogue(thisId,"name","name","name","name");

        //catObject.Save(obj);
        //return c;
        try{
            catObject.Save(obj);
        }
        catch (Exception e){
            System.out.println("Theres something");
            System.out.println(e.getLocalizedMessage());
        }
        return new ResponseEntity<Catalogue>(obj, HttpStatus.CREATED);
    }
}



