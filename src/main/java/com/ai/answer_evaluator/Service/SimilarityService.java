package com.ai.answer_evaluator.Service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SimilarityService {

    private List<String> tokenize(String text) {
        return Arrays.asList(text.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+"));
    }

    private Map<String, Double> termFrequency(List<String> words) {
        Map<String, Double> tf = new HashMap<>();
        for (String w : words) tf.put(w, tf.getOrDefault(w, 0.0) + 1);
        for (String w : tf.keySet()) tf.put(w, tf.get(w) / words.size());
        return tf;
    }

    public double cosineSimilarity(String d1, String d2) {
        List<String> w1 = tokenize(d1);
        List<String> w2 = tokenize(d2);

        Map<String, Double> tf1 = termFrequency(w1);
        Map<String, Double> tf2 = termFrequency(w2);

        Set<String> vocab = new HashSet<>();
        vocab.addAll(tf1.keySet());
        vocab.addAll(tf2.keySet());

        double dot = 0, mag1 = 0, mag2 = 0;

        for (String w : vocab) {
            double v1 = tf1.getOrDefault(w, 0.0);
            double v2 = tf2.getOrDefault(w, 0.0);
            dot += v1 * v2;
            mag1 += v1 * v1;
            mag2 += v2 * v2;
        }

        if (mag1 == 0 || mag2 == 0) return 0;
        return dot / (Math.sqrt(mag1) * Math.sqrt(mag2));
    }

    public String generateFeedback(double score) {
        if (score > 0.8) return "Excellent answer coverage";
        if (score > 0.6) return "Good answer but missing some points";
        if (score > 0.4) return "Partial understanding";
        return "Needs improvement";
    }
}