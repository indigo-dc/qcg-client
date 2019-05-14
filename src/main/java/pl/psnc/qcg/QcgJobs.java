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
public class QcgJobs {
  public List<QcgJob> list;

  public QcgJobs(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();
      if (name.equals("results")) getJobsFromJson(value);
    }
  }

  private void getJobsFromJson(JsonNode json) {
    list = new ArrayList<>();
    if (json.isArray()) {
      for (JsonNode job : json) {
        list.add(new QcgJob(job));
      }
    }
  }

}
