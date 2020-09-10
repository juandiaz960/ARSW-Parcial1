package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

/**
 * @author Santiago Carrillo 2/22/18. Juan Sebastián Díaz Salamanca
 */
@Service()
public class PrimeServiceStub implements PrimeService {
    
    CopyOnWriteArrayList<FoundPrime> primeList;

    public PrimeServiceStub(){
        primeList=new CopyOnWriteArrayList<>();
        FoundPrime primeJuan = new FoundPrime("JuanDiaz","971");
        FoundPrime primeRandom = new FoundPrime("RandomUser","1489");
        FoundPrime primeARSW = new FoundPrime("ARSWTeam","3779");
        primeList.add(primeJuan); primeList.add(primeRandom); primeList.add(primeARSW);
    }

    @Override
    public void addFoundPrime(FoundPrime foundPrime) throws PrimeServiceException {
        boolean newPrimeFound = true;
        for (FoundPrime found : primeList) {
            if (found.getPrime().equals(foundPrime.getPrime())) {
                newPrimeFound = false;
                throw new PrimeServiceException("ERROR: That Prime number was already found");
            }
        }
        if (newPrimeFound) {
            primeList.add(foundPrime);
        }
    }

    @Override
    public CopyOnWriteArrayList<FoundPrime> getFoundPrimes() throws PrimeServiceException {
        if (!primeList.isEmpty()) {
            return primeList;
        }
        throw new PrimeServiceException("ERROR: List is empty");
    }

    @Override
    public FoundPrime getPrime(String prime) throws PrimeServiceException {
        for (FoundPrime found : primeList) {
            if (found.getPrime().equals(prime)) {
                return found;
            }
        }
        throw new PrimeServiceException("ERROR: This number is not a prime number");
    }
    
    @Override
    public FoundPrime getPrimeByNumber(String primeNumber) throws PrimeServiceException {
        for(FoundPrime found: primeList){
            if(found.getPrime().equals(primeNumber)){
                return found;
            }
        }
        throw new PrimeServiceException("ERROR: This number does nto exist in the database");
    }
}
