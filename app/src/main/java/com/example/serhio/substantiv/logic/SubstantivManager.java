package com.example.serhio.substantiv.logic;

import com.example.serhio.substantiv.entities.Gender;
import com.example.serhio.substantiv.entities.Substantiv;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Serhio on 01.03.2018.
 */

public class SubstantivManager {
    private static Random random;
    private static List<Substantiv> substantivList;

    public SubstantivManager(){
        random = new Random();
     substantivList = new ArrayList<>();
    substantivList.add(new Substantiv("Mann", Gender.DER));
    substantivList.add(new Substantiv("Mutter", Gender.DIE));
    substantivList.add(new Substantiv("Moment", Gender.DER));
    substantivList.add(new Substantiv("Schule", Gender.DIE));
    substantivList.add(new Substantiv("Familie", Gender.DIE));
    substantivList.add(new Substantiv("Essen", Gender.DAS));

    }

    public  static Substantiv getNextSubstantiv(){
    int index = random.nextInt(substantivList.size());
    return  substantivList.get(index);
    }
}
