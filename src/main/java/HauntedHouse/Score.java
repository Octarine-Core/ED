package HauntedHouse;

/**
 * Represents a scoreboard.
 */
public class Score implements Comparable<Score>{
    String name;
    double score;
    int difficulty;

    /**
     * Constructor for an implicit Score object.
     * @param name player's name
     * @param score player's final score
     * @param difficulty difficulty choosen by the player.
     */
    Score(String name, double score, int difficulty){
        this.name = name;
        this.score = score;
        this.difficulty = difficulty;
    }

    /**
     * Override of compareTo method.
     * Compares this object difficulty with the specified object for order.
     * @param o the object to be compared.
     * @return a negative or a positive integer as this object's difficulty
     * is less than or greater than this specified object. If object's difficulty
     * is equal to the specified object, check if the difference between scores
     * is greater or equal than zero, returning a positive or negative integer accordingly.
     */
    @Override
    public int compareTo(Score o) {
        if(this.difficulty > o.difficulty){
            return 1;
        }
        else if(this.difficulty < o.difficulty){
            return -1;
        }
        else {
            double difference = this.score - o.score;
            if (difference == 0) return 0;
            if (difference > 0) return 1;
            return -1;
        }
    }

}
