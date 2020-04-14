import java.util.*;


class SimUnit {
    String botType;
    SimUnit(String type) {
        botType = type;
        // System.out.println(botType + " bot created. Power usage = " + powerUse());
    }
    int powerUse() {
        if ("Retention".equals(botType)) {
            return 2;
        } else {
            return 4;
        }
    }
}


class V2Radiator {
    V2Radiator(ArrayList<SimUnit> list) {
        for(int x = 0; x < 5; x++) {
            list.add(new SimUnit("V2Radiator"));
        }
    }
}


class V3Radiator extends V2Radiator {
    V3Radiator(ArrayList<SimUnit> lglist) {
        super(lglist);
        for (int g = 0; g < 10; g++) {
            lglist.add(new SimUnit("V3Radiator"));
        }
    }
}

class RetentionBot {
    RetentionBot(ArrayList<SimUnit> rlist) {
        rlist.add(new SimUnit("Retention"));
    }
}

class PowerCounter {
    int v2Power = 0;
    int v3Power = 0;    
    int retPower = 0;
    int v2Count = 0;
    int v3Count = 0;
    int retBotCount = 0;
    PowerCounter(ArrayList<SimUnit> botlist) {
        for (SimUnit bot : botlist) {
            if (bot.botType.equals("Retention")) {
                retPower += bot.powerUse();
                retBotCount++;
            } else if (bot.botType.equals("V2Radiator")) {
                v2Power += bot.powerUse();
                v2Count++;
            } else if (bot.botType.equals("V3Radiator")) {
                v3Power += bot.powerUse();
                v3Count++;
            }
        }
        System.out.println("V2Radiator bot power usage = " + v2Power + "\t\tTotal number of V2Radiator bot: " + v2Count);
        System.out.println("V3Radiator bot power usage = " + v3Power + "\t\tTotal number of V3Radiator bot: " + v3Count);
        System.out.println("Retention bot power usage = " + retPower  + "\t\tTotal number of Retention bot: " + retBotCount);
    }
}

public class TestLifeSupportSim {
    public static void main(String[] args) {
        ArrayList<SimUnit> aList = new ArrayList<SimUnit>();
        V2Radiator v2 = new V2Radiator(aList);
        V3Radiator v3 = new V3Radiator(aList);
        for (int z = 0; z < 20; z++) {
            RetentionBot ret = new RetentionBot(aList);
        }
        PowerCounter counter = new PowerCounter(aList);
    }
}