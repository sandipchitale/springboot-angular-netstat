package sandipchitale.springbootangular.netstat.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sandipchitale.springbootangular.netstat.model.NetstatLine;
import sandipchitale.springbootangular.netstat.services.NetstatService;

import java.util.List;

@RestController
public class NetstatController {
    private final NetstatService netstatService;

    public NetstatController(NetstatService netstatService) {
        this.netstatService = netstatService;
    }

    @GetMapping("/netstat")
    public List<NetstatLine> getNetstatLines() {
        return netstatService.getNetstatLines();
    }
}
