# Import file "simpool" (sizes and positions are scaled 1:4)
sketch = Framer.Importer.load("imported/simpool@4x")
Utils.globalLayers sketch

ViewController = require 'ViewController'
Views = new ViewController
	initialView: signin

		
login.onClick -> 
	Views.fadeIn(main)

scroll = ScrollComponent.wrap(list)
scroll.scrollHorizontal = false



