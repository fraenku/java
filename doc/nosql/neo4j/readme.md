
<h1>Hintergrund</h1>
Der Ursprung der Graphentheorie geht auf Leonhard Euler zurück. Euler versuchte das Königsberger Brückenproblem zu lösen. Im frühen 18. Jahrhundert ging es in dieser Fragestellung darum, ob es einen Rundweg über alle sieben Brücken über den Pregels in Königsberg geben kann, der jede Brücke genau einmal überquert. Euler näherte sich dem Problem nicht geometrisch, sondern mit Methoden, die heutzutage der Graphentheorie zugeordnet werden. Die Ufer entsprechen den Knoten, die Brücken den Verbindungen zwischen den Knoten.

<h1>Vokabular</h1>
Nodes entsprechen Entitäten oder Dingen und werden daher üblicherweise mit Substantiven bezeichnet. Diese Substantive nennen wir Label. Sie nehmen Werte wie Künstler, Band, Solokünstler an. Ein Node kann mehr als ein Label haben. Beziehungen stellen typisierte und gerichtete Verbindungen zwischen Knoten dar. Sie werden in der Regel mit Verben in Kapitälchen bezeichnet. Sowohl Knoten als auch Beziehungen können beliebig viele Properties (Eigenschaften) aufweisen. 

<h1>Cypher, Abfragesprace für Graphen</h1>
<code>
CREATE (:Solokünstler {name: 'Rodrigo González' }) - [:SPIELT_IN] -> (:Band {name: 'Rainbirds'});

MERGE (s:Solokünstler {name: 'Rodrigo González' })
MERGE (s) - [:SPIELT_IN] -> (:Band {name: 'Die Ärzte'});

MATCH (a:Solokünstler {name:"Farin Urlaub"})
MATCH (a2:Band {name:'Rainbirds'})
MATCH path=shortestPath( (a)-[:SPIELT_IN*..10]-(a2))
RETURN path
</code>
