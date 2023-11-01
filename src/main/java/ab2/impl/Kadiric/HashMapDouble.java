package ab2.impl.Kadiric;

import ab2.AbstractHashMap;

public class HashMapDouble extends AbstractHashMap {


    int brojac;
    int k_crta;

    public HashMapDouble(int size, int k)
    {
        brojac=0;
        initTable(size);
        k_crta=k;
    }

    private int hashFunction1(int key)
    {
        int adresa = key%capacity();
        if(adresa<0)
            adresa+=capacity();
        return adresa;
    }

    private int hashFunction2(int key)
    {
        return ((key%k_crta) + 1);
    }

    @Override
    public boolean put(int key, String value)
    {
        // TODO Auto-generated method stub
        int adresa = hashFunction1(key);
        if(!isEmpty(adresa))
        {
            if(getKey(adresa)==key)
                return false;
            adresa=hashFunction2(key);
            if(!isEmpty(adresa))
                return false;
        }
        brojac++;
        setKeyAndValue(adresa,key,value);
        return true;
    }

    @Override
    public String get(int key) {
        int adresa = hashFunction1(key);
        if(isEmpty(adresa))
            return null;
        int kljuc = getKey(adresa);
        if(kljuc!=key)
        {
            adresa=hashFunction2(adresa);
            if(isEmpty(adresa))
                return null;
            if(kljuc!=key)
                return null;
        }
        return getValue(adresa);
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        return brojac;
    }

}