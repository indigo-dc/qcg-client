package pl.psnc.qcg;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Piotr
 */
public class QcgQueues {
  public List<QcgQueue> list;
  public int totalJobs;
  public int totalCpus;

  public QcgQueues(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();

      if (name.equals("list")) getQueuesFromJson(value);
      if (name.equals("job_stats")) getJobStatsFromJson(value);
    }
  }

  private void getQueuesFromJson(JsonNode json) {
    list = new ArrayList<>();
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();
      list.add(new QcgQueue(name, value));
    }
  }

  private void getJobStatsFromJson(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();

      if (name.equals("total")) getTotalsFromJson(value);
      //if (name.equals("states")) ...
    }
  }

  private void getTotalsFromJson(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();

      if (name.equals("jobs")) this.totalJobs = value.asInt();
      if (name.equals("cpus")) this.totalCpus = value.asInt();
    }
  }

  @Override
  public String toString() {
    String s = "{Queues:[";
    for (QcgQueue queue : list) s += queue.toString();
    return s + "] " +
      "totalJobs=" + totalJobs + " " +
      "totalCpus=" + totalCpus + "}";
  }

}