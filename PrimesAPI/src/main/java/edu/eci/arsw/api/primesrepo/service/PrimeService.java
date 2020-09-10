package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@Service("PrimeService")
public interface PrimeService
{

    void addFoundPrime(FoundPrime foundPrime) throws PrimeServiceException;

    CopyOnWriteArrayList<FoundPrime> getFoundPrimes() throws PrimeServiceException;

    FoundPrime getPrime(String prime) throws PrimeServiceException;
    
    FoundPrime getPrimeByNumber( String primeNumbe ) throws PrimeServiceException;

}
