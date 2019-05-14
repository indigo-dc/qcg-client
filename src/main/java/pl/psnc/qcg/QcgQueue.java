package pl.psnc.qcg;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Piotr
 */
public class QcgQueue {
  public String  name;
  public boolean isDefault;
  public String  def_walltime;
  public String  max_time;
  public int     min_nodes;
  public int     max_nodes;
  public int     nodes;
  public int     priority;
  public String  state;
  public int     cpus;

  public QcgQueue(String name, JsonNode json) {
    this.name = name;
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String fieldName = field.getKey();
      JsonNode value = field.getValue();

      if (fieldName.equals("default")) this.isDefault = value.asBoolean();
      if (fieldName.equals("def_walltime")) this.def_walltime = value.asText();
      if (fieldName.equals("max_time")) this.max_time = value.asText();
      if (fieldName.equals("min_nodes")) this.min_nodes = value.asInt();
      if (fieldName.equals("max_nodes")) this.max_nodes = value.asInt();
      if (fieldName.equals("nodes")) this.nodes = value.asInt();
      if (fieldName.equals("priority")) this.priority = value.asInt();
      if (fieldName.equals("state")) this.state = value.asText();
      if (fieldName.equals("cpus")) this.cpus = value.asInt();
    }
  }

  @Override
  public String toString() {
    return "Queue " + name      + "{" +
      "isDefault="    + isDefault    + " " +
      "def_walltime=" + def_walltime + " " +
      "max_time="     + max_time     + " " +
      "min_nodes="    + min_nodes    + " " +
      "max_nodes="    + max_nodes    + " " +
      "nodes="        + nodes        + " " +
      "priority="     + priority     + " " +
      "state="        + state        + " " +
      "cpus="         + cpus         + "}";
  }

}
