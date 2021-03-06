/*
 * This file is part of Gradoop.
 *
 * Gradoop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gradoop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gradoop. If not, see <http://www.gnu.org/licenses/>.
 */

package org.gradoop.flink.model.impl.operators.statistics;

import org.apache.flink.api.java.DataSet;
import org.gradoop.common.model.impl.id.GradoopId;
import org.gradoop.flink.model.api.operators.UnaryGraphToValueOperator;
import org.gradoop.flink.model.impl.LogicalGraph;
import org.gradoop.flink.model.impl.functions.epgm.Id;
import org.gradoop.flink.model.impl.functions.epgm.SourceId;
import org.gradoop.flink.model.impl.operators.statistics.functions.SetOrCreateWithCount;
import org.gradoop.flink.model.impl.tuples.WithCount;

/**
 * Computes the outgoing degree for each vertex.
 */
public class OutgoingVertexDegrees
  implements UnaryGraphToValueOperator<DataSet<WithCount<GradoopId>>> {

  @Override
  public DataSet<WithCount<GradoopId>> execute(LogicalGraph graph) {
    return new EdgeValueDistribution<>(new SourceId<>()).execute(graph)
      .rightOuterJoin(graph.getVertices().map(new Id<>()))
      .where(0).equalTo("*")
      .with(new SetOrCreateWithCount());
  }
}
