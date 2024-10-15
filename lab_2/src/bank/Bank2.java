package bank;

/**
 *
 * @author hooman
 */

import java.util.*;

class Bank2 {
    public String name;
    public HashMap<Integer, Account> accounts;

    public Bank2(String name) {
        this.name = name;
        accounts = new HashMap<Integer, Account>();
    }

    // find the account for the given id. Returns null if not found
    public Account findAccount(int id) {
        return accounts.get(id);
    }

    public boolean addAccount(Account account) {
        if (findAccount(account.getID()) == null) {
            accounts.put(account.getID(), account);
            return true;
        } else {
            return false;
        }
    }

    public void printAccounts() {
        for (Account acc : accounts.values()) {
            acc.print();
        }
    }

    public HashMap<String, Double> getTotalBalancePerCity() {
        HashMap<String, Double> totalBalances = new HashMap<>();
        for (Account acc : accounts.values()) {
            Double balance = totalBalances.get(acc.getCity());
            if (balance == null) {
                totalBalances.put(acc.getCity(), acc.getBalance());
            } else {
                totalBalances.put(acc.getCity(), acc.getBalance() + balance);
            }
        }
        return totalBalances;
    }

    public HashMap<String, Integer> getTotalCountPerCity() {
        HashMap<String, Integer> totalcounts = new HashMap<>();
        for (Account acc : accounts.values()) {
            Integer count = totalcounts.get(acc.getCity());
            if (count == null) {
                totalcounts.put(acc.getCity(), 1);
            } else {
                totalcounts.put(acc.getCity(), count + 1);
            }
        }
        return totalcounts;
    }

    public void reportCity(HashMap<String, Double> balances, HashMap<String, Integer> counts) {
        System.out.println();
        System.out.println("\n City \t \t Total Balance \t \t Average Balance");
        for (String city : balances.keySet()) {
            System.out.println(
                    city + "\t \t " + balances.get(city) + " \t \t " + balances.get(city) / (double) counts.get(city));
        }
    }

    public HashMap<Integer, Integer> getTotalCountPerRange(ArrayList<Integer> ranges) {
        HashMap<Integer, Integer> totalCountsInRange = new HashMap<>();
        int i = 1;
        for (Account acc : accounts.values()) {
            int min = ranges.get(i - 1);
            int max = ranges.get(i);
            Integer index = totalCountsInRange.get(i - 1);
            if (index == null) {
                if (acc.getBalance() >= min && acc.getBalance() <= max) {
                    totalCountsInRange.put(i - 1, index);
                }
            }
        }
        return totalCountsInRange;
    }

    public void reportRanges(ArrayList<Integer> ranges, HashMap<Integer, Integer> countsPerRange) {

    }
}