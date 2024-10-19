package main

import (
	"encoding/json"
	"net/http"
	"strconv"
)

type Response struct {
	Result float64 `json:"result"`
	Error  string  `json:"error,omitempty"`
}

func main() {
	http.HandleFunc("/add", addHandler)
	http.HandleFunc("/subtract", subtractHandler)
	http.HandleFunc("/multiply", multiplyHandler)
	http.HandleFunc("/divide", divideHandler)
	http.ListenAndServe(":8080", nil)
}

func addHandler(w http.ResponseWriter, r *http.Request) {
	calculate(w, r, func(a, b float64) float64 {
		return a + b
	})
}

func subtractHandler(w http.ResponseWriter, r *http.Request) {
	calculate(w, r, func(a, b float64) float64 {
		return a - b
	})
}

func multiplyHandler(w http.ResponseWriter, r *http.Request) {
	calculate(w, r, func(a, b float64) float64 {
		return a * b
	})
}

func divideHandler(w http.ResponseWriter, r *http.Request) {
	query := r.URL.Query()
	aStr := query.Get("a")
	bStr := query.Get("b")

	a, err := strconv.ParseFloat(aStr, 64)
	if err != nil {
		respondWithError(w, "Invalid parameter for 'a'")
		return
	}

	b, err := strconv.ParseFloat(bStr, 64)
	if err != nil {
		respondWithError(w, "Invalid parameter for 'b'")
		return
	}

	if b == 0 {
		respondWithError(w, "Division by zero")
		return
	}

	result := a / b
	response := Response{
		Result: result,
	}
	jsonResponse(w, response)
}

func calculate(w http.ResponseWriter, r *http.Request, op func(a, b float64) float64) {
	query := r.URL.Query()
	aStr := query.Get("a")
	bStr := query.Get("b")

	a, err := strconv.ParseFloat(aStr, 64)
	if err != nil {
		respondWithError(w, "Invalid parameter for 'a'")
		return
	}

	b, err := strconv.ParseFloat(bStr, 64)
	if err != nil {
		respondWithError(w, "Invalid parameter for 'b'")
		return
	}

	result := op(a, b)
	response := Response{
		Result: result,
	}
	jsonResponse(w, response)
}

func respondWithError(w http.ResponseWriter, errorMsg string) {
	response := Response{
		Error: errorMsg,
	}
	jsonResponse(w, response)
}

func jsonResponse(w http.ResponseWriter, response Response) {
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}
