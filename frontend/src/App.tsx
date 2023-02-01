import Handlebars from "handlebars"
import { Component } from 'react'
import './App.css'

var oldHref = "http://localhost:3000"
var turn = 0

interface Cell {
  text: String;
  worker: String;
  link: String;
  image: String;
}

interface Cells {
  cells: Array<Cell>,
  template: HandlebarsTemplateDelegate<any>,
  instructions: String
  godOne: String
  godTwo: String
  godOneRules: String
  godTwoRules: String
  image: String
}

interface Props {
}

class App extends Component<Props, Cells> {
  constructor(props: Props) {
    super(props);
    this.state = {
      cells: [
        { text: "", worker: "", link: "/gods?x=0&y=0&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=1&y=0&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=2&y=0&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=3&y=0&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=4&y=0&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=0&y=1&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=1&y=1&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=2&y=1&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=3&y=1&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=4&y=1&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=0&y=2&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=1&y=2&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=2&y=2&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=3&y=2&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=4&y=2&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=0&y=3&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=1&y=3&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=2&y=3&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=3&y=3&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=4&y=3&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=0&y=4&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=1&y=4&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=2&y=4&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=3&y=4&w=P0W0", image: ""  },
        { text: "", worker: "", link: "/gods?x=4&y=4&w=P0W0", image: ""  },
      ],
      template: this.loadTemplate(),
      instructions: "It is Player 0's turn",
      godOne: "",
      godTwo: "",
      godOneRules: "",
      godTwoRules: "",
      image: ""
    };
  }

// have cells be move or build with correct params
// those methods return the json obj of board
// update ui with convertToCell

  loadTemplate (): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  convertToCell(p: any): Array<Cell> {
    // console.log(p);
    const newCells: Array<Cell> = [];
    for (var i = 0; i < p["cells"].length; i++) {
      for (var j = 0; j < p["cells"][i].length; j++) {
        var c: Cell = {
          text: p["cells"][i][j]["text"],
          worker: p["cells"][i][j]["worker"],
          link: p["cells"][i][j]["link"],
          image: p["cells"][i][j]["image"],
        };
        
        newCells.push(c);
      }
    }

    return newCells;
  }

  getGodOne(p: any): String {
    return p["godOne"]
  }

  getGodTwo(p: any): String {
    return p["godTwo"]
  }

  getGodOneRules(p: any): String {
    return p["godOneRules"]
  }

  getGodTwoRules(p: any): String {
    return p["godTwoRules"]
  }

  getTurn(p: any): String {
    return p["turn"]
  }

  getWinner(p: any): String | undefined {
    return p["winner"]
  }

  getInstr(turn: String, winner: String | undefined) {
    if (winner === undefined) return "It is Player " + turn + "'s turn."
    else return "Player " + winner + " wins!"
  }

  async newGame() {
    const response = await fetch("newgame");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const godOneRules = this.getGodOneRules(json);
    const godTwoRules = this.getGodTwoRules(json);
    this.setState({ cells: newCells, godOneRules: godOneRules, godTwoRules: godTwoRules, instructions: "Choose gods and then set workers!" });
  }

  async gods(url: String) {
    const href = "gods?" + url.split("?")[1];
    const response = await fetch(href);
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const godOne = this.getGodOne(json);
    const godTwo = this.getGodTwo(json);
    const godOneRules = this.getGodOneRules(json);
    const godTwoRules = this.getGodTwoRules(json);
    const turn = this.getTurn(json);
    const winner = this.getWinner(json);
    this.setState({cells: newCells, instructions: "Choose gods and then set workers!", godOne: godOne, godTwo: godTwo,
      godOneRules: godOneRules, godTwoRules: godTwoRules, image: "p" + turn + ".png"})
  }

  async play(url: String) {
    const href = "play?" + url.split("?")[1];
    const response = await fetch(href);
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const godOne = this.getGodOne(json);
    const godTwo = this.getGodTwo(json);
    const godOneRules = this.getGodOneRules(json);
    const godTwoRules = this.getGodTwoRules(json);
    const turn = this.getTurn(json);
    const winner = this.getWinner(json);
    const instr = this.getInstr(turn, winner);
    this.setState({ cells: newCells, instructions: instr, godOne: godOne, godTwo: godTwo,
      godOneRules: godOneRules, godTwoRules: godTwoRules, image: "p" + turn + ".png" })
  }

  async build(url: String) {
    const href = "build?" + url.split("?")[1];
    const response = await fetch(href);
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const godOne = this.getGodOne(json);
    const godTwo = this.getGodTwo(json);
    const godOneRules = this.getGodOneRules(json);
    const godTwoRules = this.getGodTwoRules(json);
    const turn = this.getTurn(json);
    const winner = this.getWinner(json);
    const instr = this.getInstr(turn, winner);
    this.setState({ cells: newCells, instructions: instr, godOne: godOne, godTwo: godTwo,
      godOneRules: godOneRules, godTwoRules: godTwoRules, image: "p" + turn + ".png" })
  }

  async undo() {
    const response = await fetch("undo");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr, image: "p" + turn + ".png" })
  }

  async switch() {
    if (
      window.location.href === "http://localhost:3000/newgame" &&
      oldHref !== window.location.href
    ) {
      this.newGame();
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/play" &&
      oldHref !== window.location.href
    ) {
      this.play(window.location.href);
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/gods" &&
      oldHref !== window.location.href
    ) {
      this.gods(window.location.href);
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/build" &&
      oldHref !== window.location.href
    ) {
      this.build(window.location.href);
      oldHref = window.location.href;
    } else if (
      window.location.href === "http://localhost:3000/undo" &&
      oldHref !== window.location.href
    ) {
      this.undo();
      oldHref = window.location.href;
    }
  };

  render() {
    this.switch();
    return (
      <div className="App">
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({ cells: this.state.cells, instructions: this.state.instructions, godOne: this.state.godOne, godTwo: this.state.godTwo, godOneRules: this.state.godOneRules, godTwoRules: this.state.godTwoRules, image: this.state.image }),
          }}
        />
      </div>
    )
  };
};

export default App;
