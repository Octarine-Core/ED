package HauntedHouse;

public class Score implements Comparable<Score>{
    String name;
    double score;
    int difficulty;

    Score(String name, double score, int difficulty){
        this.name = name;
        this.score = score;
        this.difficulty = difficulty;
    }

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
