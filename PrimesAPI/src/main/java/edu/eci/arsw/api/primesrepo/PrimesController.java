package edu.eci.arsw.api.primesrepo;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Santiago Carrillo - Juan Sebastián Díaz Salamanca
 * 2/22/18.
 */
@RestController
@RequestMapping( value = "/primes")
public class PrimesController
{

    @Autowired
    PrimeService primeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getFoundPrimes() {
        try {
            return new ResponseEntity<>(primeService.getFoundPrimes(), HttpStatus.ACCEPTED);
        } catch (PrimeServiceException e) {
            return new ResponseEntity<>("404 NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addFoundPrime(@RequestBody FoundPrime fp){
        try {
            primeService.addFoundPrime(fp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PrimeServiceException ex) {
            return new ResponseEntity<>("500 FORBIDDEN", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{primenumber}", method = RequestMethod.GET)
    public ResponseEntity<?> getPrimeByNumber(@PathVariable String primenumber) {
        try {
            return new ResponseEntity<>(primeService.getPrimeByNumber(primenumber), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("404 NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }
}
