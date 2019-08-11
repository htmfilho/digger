package digger.web;

import digger.model.Datasource;
import digger.repository.DatasourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/error")
    public String getResources() {
        return "error";
    }
}