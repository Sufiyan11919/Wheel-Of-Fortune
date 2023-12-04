import React, { useEffect, useState } from "react";
import "./App.css";
import axios from "axios";
import { initializeApp } from "firebase/app";
import { getAuth, signInWithPopup, GoogleAuthProvider } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyCjMYiU_fOXVL41n2CDbDwwMG1TUTjLWHE",
  authDomain: "wheeloffortunereact-407102.firebaseapp.com",
  projectId: "wheeloffortunereact-407102",
  storageBucket: "wheeloffortunereact-407102.appspot.com",
  messagingSenderId: "721481853009",
  appId: "1:721481853009:web:526d367d7dd6a279603070",
  measurementId: "G-5LB9HQ6KKV"
};
initializeApp(firebaseConfig);

const signInWithGoogle = () => {
  const provider = new GoogleAuthProvider();
  const auth = getAuth();
  signInWithPopup(auth, provider)
    .then((result) => {
      console.log(result.user);
    })
    .catch((error) => {
      console.error(error);
    });
};

function App() {
  const [userId, setUserId] = useState("");
  const [pageNo, setPageNo] = useState(0);
  const [records, setRecords] = useState([]);
  const [userRecords, setUserRecords] = useState([]);
  const [phrases] = useState(["hello", "hi", "same"]);
  const [phrase, setPhrase] = useState("");
  const [hiddenPhrase, setHiddenPhrase] = useState("");
  const [previousGuesses, setPreviousGuesses] = useState("");
  const [maxGuesses] = useState(7);
  const [wrongGuesses, setWrongGuesses] = useState(0);
  const [gameOver, setGameOver] = useState(false);
  const [solved, setSolved] = useState(false);
  const [handle, setHandle] = useState("");
  const [blinkColors, setBlinkColors] = useState([]);

  useEffect(() => {
    const auth = getAuth();
    const unsubscribe = auth.onAuthStateChanged((user) => {
      if (user) {
        setUserId(user.displayName);
      } else {
        console.log("No user is signed in.");
      }
    });
    return () => unsubscribe();
  }, []);

  useEffect(() => {
    newGame();
  }, [phrases]);

  const displayAllRecords = async () => {
    try {
      const response = await axios.get("https://wheeloffortunereact-407102.uw.r.appspot.com/findAllGameRecord");
      setRecords(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const displayAllUserRecords = async () => {
    try {
      const response = await axios.get("https://wheeloffortunereact-407102.uw.r.appspot.com/findAllUser");
      setUserRecords(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const generateHiddenPhrase = (currentPhrase) => {
    const updatedHiddenPhrase = currentPhrase
      .split("")
      .map((char) => (char.match(/[a-zA-Z]/) ? "*" : char))
      .join("");
    setHiddenPhrase(updatedHiddenPhrase);
  };

  const updateBlinkColors = () => {
    const newBlinkColors = phrase.split("").map((char, index) => {
      if (char.match(/[a-zA-Z]/)) {
        return hiddenPhrase[index] === char.toLowerCase() ? "green" : "red";
      }
      return "transparent";
    });
    setBlinkColors(newBlinkColors);
  };

  const processGuess = (guess) => {
    if (previousGuesses.includes(guess.toLowerCase())) {
      alert("You already guessed that letter! Please a different letter.");
      return;
    }

    const isCorrectGuess = phrase.toLowerCase().includes(guess.toLowerCase());
    const isGameOver = wrongGuesses + 1 >= maxGuesses;

    if (isCorrectGuess) {
      const updatedHiddenPhrase = phrase
        .split("")
        .map((char, index) =>
          char.match(/[a-zA-Z]/) && hiddenPhrase[index] === "*"
            ? phrase[index].toLowerCase() === guess.toLowerCase()
              ? phrase[index]
              : "*"
            : char
        )
        .join("");

      setHiddenPhrase(updatedHiddenPhrase);

      if (updatedHiddenPhrase.toLowerCase() === phrase.toLowerCase()) {
        setGameOver(true);
        setSolved(true);
      }
    } else {
      setWrongGuesses(wrongGuesses + 1);

      if (isGameOver) {
        setGameOver(true);
      }
    }

    setPreviousGuesses(previousGuesses + guess.toLowerCase() + ",");
    updateBlinkColors();
  };

  const handleNameChange = (event) => {
    setHandle(event.target.value);
  };

  const handleSubmitName = async () => {
    setPageNo(3);
    await displayAllRecords();
    await displayAllUserRecords();

    const auth = getAuth();
    const user = auth.currentUser;

    try {
      await axios.post("https://wheeloffortunereact-407102.uw.r.appspot.com/saveUser", {
        userId: user.displayName,
        handle: handle,
        email: user.email,
        date: new Date().toISOString().slice(0, 10),
      });

      await axios.post("https://wheeloffortunereact-407102.uw.r.appspot.com/saveGameRecord", {
        userId: user.displayName,
        score: maxGuesses - wrongGuesses,
        date: new Date().toISOString().slice(0, 10),
      });
    } catch (error) {
      console.error(error);
    }
  };

  const displayMyGames = async () => {
    try {
      const response = await axios.get(`https://wheeloffortunereact-407102.uw.r.appspot.com/findByUserId?userId=${userId}`);
      setRecords(response.data);
      setPageNo(4);
    } catch (error) {
      console.error(error);
    }
  };

  const displayLeaderboard = async () => {
    try {
      const response = await axios.get("https://wheeloffortunereact-407102.uw.r.appspot.com/findAllGameRecord");
      setRecords(response.data);
      setPageNo(5);
    } catch (error) {
      console.error(error);
    }
  };

  const newGame = () => {
    setHiddenPhrase("");
    setPreviousGuesses("");
    setWrongGuesses(0);
    setGameOver(false);
    setSolved(false);
    setBlinkColors([]); 

    const randomIndex = Math.floor(Math.random() * phrases.length);
    setPhrase(phrases[randomIndex]);
    generateHiddenPhrase(phrases[randomIndex]);
  };

  return (
    <>
      {!userId && (
        <button type="button" onClick={signInWithGoogle}>
          Sign in with Google
        </button>
      )}
      {userId && (
        <div className="custom-app">
          {!gameOver ? (
            <div className="custom-app-container">
              <h1>Wheel of Fortune</h1>
              <p className="game-description">
                Welcome to Wheel of Fortune! Guess the hidden phrase by entering
                letters. Correct guesses will reveal the letters, while wrong
                guesses will bring you one step closer to losing the game.
              </p>
              <div className="custom-phrase">
                {hiddenPhrase.split("").map((char, index) => (
                  <span key={index} style={{ color: blinkColors[index] }}>
                    {char}
                  </span>
                ))}
              </div>
              <div className="custom-previous-guesses">
                Previous Guesses: {previousGuesses}
              </div>
              <input
                type="text"
                maxLength="1"
                onChange={(e) => {
                  const guess = e.target.value;
                  if (guess.match(/[a-zA-Z]/) && guess.length === 1) {
                    processGuess(guess);
                    e.target.value = "";
                  } else {
                    alert("Please enter Alphabet only");
                    e.target.value = "";
                  }
                }}
              />
              <div className="custom-wrong-guesses">
                Wrong Guesses: {wrongGuesses}
              </div>
            </div>
          ) : (
            <div className="custom-end-game-message">
              {solved && pageNo === 0 && (
                <div className="custom-win-message">YOU WON!!</div>
              )}
              {!solved && pageNo === 0 && (
                <div className="custom-lose-message">Game Over!</div>
              )}
              {pageNo === 0 && (
                <div>
                  <p>Do you want to save your game record?</p>
                  <button type="button" onClick={() => setPageNo(2)}>
                    Yes
                  </button>
                  <button type="button" onClick={() => setPageNo(1)}>
                    No
                  </button>
                </div>
              )}
              {pageNo === 1 && <button onClick={newGame}>New Game</button>}

              {pageNo === 2 && (
                <div>
                  <label htmlFor="custom-nameInput">Enter your name:</label>
                  <input
                    type="text"
                    id="custom-nameInput"
                    value={handle}
                    onChange={handleNameChange}
                  />
                  <button type="button" onClick={handleSubmitName}>
                    Submit Name
                  </button>
                </div>
              )}

              {pageNo === 3 && (
                <div>
                  <button onClick={displayMyGames}>Display My Games</button>
                  <button onClick={displayLeaderboard}>
                    Display Leaderboard
                  </button>
                </div>
              )}

              {pageNo === 4 &&
                userRecords.map((record) => (
                  <div className="custom-record-item" key={record.id}>
                    <h3>{record.id}</h3>
                    <p>by {record.score}</p>
                    <p> {record.date}</p>
                    <p> {record.userId}</p>
                  </div>
                ))}

                {pageNo === 5 && (
                  <div className="page-container">
                    <>
                      <h1 className="header_leaderBoard">Leaderboard</h1>
                      <table className="leaderboard-table">
                        <thead>
                          <tr>
                            <th>ID</th>
                            <th>Score</th>
                            <th>Date</th>
                            <th>User ID</th>
                          </tr>
                        </thead>
                        <tbody>
                          {records.map((record) => (
                            <tr className="custom-record-item" key={record.id}>
                              <td>{record.id}</td>
                              <td>{record.score}</td>
                              <td>{record.date}</td>
                              <td>{record.userId}</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </>
                  </div>
                )}
            </div>
          )}
        </div>
      )}
    </>
  );
}

export default App;