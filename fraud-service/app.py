from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/score', methods=['POST'])
def score_transaction():
    data = request.json
    amount = data.get('amount', 0)
    user_id = data.get('userId')
    
    # Simple Rule-Based Logic (MVP for AI Integration)
    # Flag if amount is over 10000 or specific user
    is_fraud = False
    if amount > 10000:
        is_fraud = True
    
    # Simulate a score (0.0 to 1.0)
    risk_score = 0.95 if is_fraud else 0.10
    
    return jsonify({
        "isFraud": is_fraud,
        "riskScore": risk_score
    })

if __name__ == '__main__':
    app.run(port=5000)