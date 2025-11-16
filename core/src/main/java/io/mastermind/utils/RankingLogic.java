package io.mastermind.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import io.mastermind.objects.Ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingLogic {
    public static final String COMMA_DELIMITER = ",";

    private final ArrayList<Ranking> ranking = new ArrayList<Ranking>();
    private final FileHandle file;

    public RankingLogic() {
        String caminho = "ranking.csv";
        if (isGwt()) {
            this.file = Gdx.files.internal(caminho); // Só leitura no HTML5
        } else {
            this.file = Gdx.files.local(caminho); // Escrita/leitura no desktop/mobile
            if (!this.file.exists()) {
                FileHandle parent = this.file.parent();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                this.file.writeString("", false);
            }
        }
        lerCSV();
    }

    private boolean isGwt() {
        return Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.WebGL;
    }

    public void lerCSV() {
        ranking.clear();
        if (!file.exists()) return;

        String content;
        try {
            content = file.readString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (content == null || content.trim().isEmpty()) return;

        String[] lines = content.split("\\r?\\n");
        for (String linha : lines) {
            if (linha == null || linha.trim().isEmpty()) continue;
            String[] values = linha.split(COMMA_DELIMITER);
            Ranking linhaRanking = new Ranking(values[0], values[1], values[2]);
            ranking.add(linhaRanking);
        }
    }

    public synchronized void add(String name, Integer attemptNumber, String date) {
        if (name == null) name = "";
        if (attemptNumber == null) attemptNumber = 0;

        String record = name + COMMA_DELIMITER + attemptNumber + COMMA_DELIMITER + date;
        Ranking linhaRanking = new Ranking(name, attemptNumber, date);
        ranking.add(linhaRanking);

        try {
            file.writeString(record + "\n", true);
        } catch (Exception e) {
            throw new RuntimeException("Unable to write ranking entry", e);
        }
    }

    public List<Ranking> getRanking() {
        ArrayList<Ranking> temp = new ArrayList<Ranking>(ranking);

        Collections.sort(temp, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking a, Ranking b) {
                return Integer.compare(a.getQuantity(), b.getQuantity());
            }
        });

        return temp.subList(0, Math.min(15, temp.size()));
    }
}
