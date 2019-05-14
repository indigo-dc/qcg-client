package pl.psnc.qcg;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Piotr
 */
public class QcgResources {
  public String date;
  public QcgNodes nodes;
  public QcgQueues queues;
  public String scheduler;
  public String started;
  public String version;

  public QcgResources(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();

      if (name.equals("date")) this.date = value.asText();
      if (name.equals("nodes")) this.nodes = new QcgNodes(value);
      if (name.equals("queues")) this.queues = new QcgQueues(value);
      if (name.equals("config")) getConfigFromJson(value);
    }
  }

  private void getConfigFromJson(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String fieldName = field.getKey();
      JsonNode value = field.getValue();
      if (fieldName.equals("scheduler")) this.scheduler = value.asText();
      if (fieldName.equals("started")) this.started = value.asText();
      if (fieldName.equals("version")) this.version = value.asText();
    }
  }

  @Override
  public String toString() {
    return "Resource{" +
      "date="      + date              + " " +
      "nodes="     + nodes.toString()  + " " +
      "queues="    + queues.toString() + " " +
      "scheduler=" + scheduler         + " " +
      "started="   + started           + " " +
      "version="   + version           + "}";
  }

}
