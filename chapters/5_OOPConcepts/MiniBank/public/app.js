// State
let currentUser = {
    name: '',
    acNo: 0,
    pass: 0,
    balance: 0
};

// Helpers
const $ = id => document.getElementById(id);

function switchTab(tab) {
    $('login-error').innerText = '';
    $('reg-error').innerText = '';
    $('reg-success').innerText = '';

    if (tab === 'login') {
        $('login-form').classList.remove('hidden');
        $('register-form').classList.add('hidden');
        document.querySelectorAll('.tab')[0].classList.add('active');
        document.querySelectorAll('.tab')[1].classList.remove('active');
    } else {
        $('login-form').classList.add('hidden');
        $('register-form').classList.remove('hidden');
        document.querySelectorAll('.tab')[0].classList.remove('active');
        document.querySelectorAll('.tab')[1].classList.add('active');
    }
}

function showModal(id) {
    $(id).classList.add('active');
}

function closeModal(id) {
    $(id).classList.remove('active');
    // clear inputs inside modal
    const inputs = $(id).querySelectorAll('input');
    inputs.forEach(i => i.value = '');
}

// API Calls
async function apiCall(endpoint, data) {
    const params = new URLSearchParams(data);
    try {
        const response = await fetch(`/api/${endpoint}`, {
            method: 'POST',
            body: params
        });
        return await response.json();
    } catch (e) {
        return { success: false, message: 'Network error' };
    }
}

// Actions
async function handleRegister(e) {
    e.preventDefault();
    const name = $('reg-name').value;
    const pass = $('reg-pass').value;

    $('reg-error').innerText = 'Creating account...';
    const res = await apiCall('createAccount', { name, pass });

    if (res.success) {
        $('reg-error').innerText = '';
        $('reg-success').innerText = `Success! Your Account No is ${res.acNo}. Please login.`;
        $('reg-name').value = '';
        $('reg-pass').value = '';
    } else {
        $('reg-error').innerText = res.message || 'Registration failed';
    }
}

async function handleLogin(e) {
    e.preventDefault();
    const acNo = $('login-acno').value;
    const pass = $('login-pass').value;

    $('login-error').innerText = 'Authenticating...';
    const res = await apiCall('login', { acNo, pass });

    if (res.success) {
        currentUser.name = res.name;
        currentUser.acNo = acNo;
        currentUser.pass = pass;
        currentUser.balance = res.balance;

        $('login-error').innerText = '';
        $('dash-acno').innerText = acNo;
        $('dash-balance').innerText = `Rs. ${res.balance}`;
        $('dash-name').innerText = res.name;

        $('auth-view').classList.remove('active');
        $('dashboard-view').classList.add('active');
        
        loadTransactions();
    } else {
        $('login-error').innerText = 'Invalid Account No or Passcode';
    }
}

function logout() {
    currentUser = { name: '', acNo: 0, pass: 0, balance: 0 };
    $('dashboard-view').classList.remove('active');
    $('auth-view').classList.add('active');
    $('login-acno').value = '';
    $('login-pass').value = '';
    $('dash-success').innerText = '';
    $('dash-error').innerText = '';
}

async function handleDeposit() {
    const amount = $('deposit-amount').value;
    if (!amount || amount <= 0) return;

    const res = await apiCall('deposit', { acNo: currentUser.acNo, amount });
    if (res.success) {
        currentUser.balance = res.balance;
        $('dash-balance').innerText = `Rs. ${res.balance}`;
        $('dash-success').innerText = `Successfully deposited Rs. ${amount}`;
        $('dash-error').innerText = '';
        closeModal('deposit-modal');
        loadTransactions();
    } else {
        $('dash-error').innerText = res.message;
        $('dash-success').innerText = '';
    }
}

async function handleWithdraw() {
    const amount = $('withdraw-amount').value;
    if (!amount || amount <= 0) return;

    const res = await apiCall('withdraw', { acNo: currentUser.acNo, pass: currentUser.pass, amount });
    if (res.success) {
        currentUser.balance = res.balance;
        $('dash-balance').innerText = `Rs. ${res.balance}`;
        $('dash-success').innerText = `Successfully withdrew Rs. ${amount}`;
        $('dash-error').innerText = '';
        closeModal('withdraw-modal');
        loadTransactions();
    } else {
        $('dash-error').innerText = res.message;
        $('dash-success').innerText = '';
    }
}

async function handleTransfer() {
    const recepientAcNo = $('transfer-acno').value;
    const amount = $('transfer-amount').value;
    if (!amount || amount <= 0 || !recepientAcNo) return;

    const res = await apiCall('transfer', {
        senderAcNo: currentUser.acNo,
        pass: currentUser.pass,
        recepientAcNo,
        amount
    });

    if (res.success) {
        currentUser.balance = res.balance;
        $('dash-balance').innerText = `Rs. ${res.balance}`;
        $('dash-success').innerText = `Successfully transferred Rs. ${amount} to Acc ${recepientAcNo}`;
        $('dash-error').innerText = '';
        closeModal('transfer-modal');
        loadTransactions();
    } else {
        $('dash-error').innerText = res.message;
        $('dash-success').innerText = '';
    }
}

// Graph Rendering
async function loadTransactions() {
    const res = await apiCall('transactions', { acNo: currentUser.acNo, pass: currentUser.pass });
    if (res.success && res.transactions) {
        renderGraph(res.transactions);
    }
}

function renderGraph(transactions) {
    const container = $('tx-graph-container');
    const nodesContainer = $('tx-nodes');
    const svgLines = $('tx-svg-lines');
    const noTxMsg = $('no-tx-msg');
    
    const gridLines = $('grid-lines');
    const yAxisLabels = $('y-axis-labels');
    const xAxisLabels = $('x-axis-labels');
    
    nodesContainer.innerHTML = '';
    svgLines.innerHTML = '';
    gridLines.innerHTML = '';
    yAxisLabels.innerHTML = '';
    xAxisLabels.innerHTML = '';
    
    if (!transactions || transactions.length === 0) {
        noTxMsg.classList.remove('hidden');
        return;
    }
    
    noTxMsg.classList.add('hidden');
    
    // Sort transactions by date (newest to oldest to calculate balance backwards)
    transactions.sort((a, b) => new Date(b.date) - new Date(a.date));
    
    let currentBal = currentUser.balance;
    const dataPoints = [];
    
    // Add current point
    const now = new Date();
    dataPoints.push({
        type: 'CURRENT',
        amount: 0,
        date: now,
        balance: currentBal,
        isTx: false
    });
    
    transactions.forEach(tx => {
        const txDate = new Date(tx.date);
        // Balance after this transaction is the currentBal
        dataPoints.push({
            type: tx.type,
            amount: tx.amount,
            date: txDate,
            balance: currentBal,
            isTx: true
        });
        
        // Reverse the transaction to find balance before it
        if (tx.type === 'DEPOSIT' || tx.type === 'TRANSFER_IN') {
            currentBal -= tx.amount;
        } else if (tx.type === 'WITHDRAW' || tx.type === 'TRANSFER_OUT') {
            currentBal += tx.amount;
        }
    });
    
    // Reverse to chronological (oldest to newest)
    dataPoints.reverse();
    
    const minTime = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000).getTime();
    const maxTime = now.getTime();
    
    // Filter out data points older than 7 days if any exist
    const recentPoints = dataPoints.filter(p => p.date.getTime() >= minTime);
    
    // If the oldest transaction is after minTime, we should prepend a starting balance point
    if (recentPoints.length > 0 && recentPoints[0].date.getTime() > minTime) {
        // Find what the balance was just before the first transaction in recentPoints
        // This is exactly the currentBal we computed at the end of the backwards loop
        recentPoints.unshift({
            type: 'START',
            amount: 0,
            date: new Date(minTime),
            balance: currentBal,
            isTx: false
        });
    }

    let minBal = Math.min(...recentPoints.map(p => p.balance));
    let maxBal = Math.max(...recentPoints.map(p => p.balance));
    
    if (minBal === maxBal) {
        minBal -= 100;
        maxBal += 100;
    } else {
        const padding = (maxBal - minBal) * 0.1;
        minBal = Math.max(0, minBal - padding); // don't go below 0 unless needed
        maxBal += padding;
    }
    
    // Draw Y-Axis (4 grid lines)
    for (let i = 0; i <= 4; i++) {
        const percent = (i / 4) * 100;
        const balValue = minBal + ((maxBal - minBal) * (i / 4));
        
        gridLines.innerHTML += `<div class="grid-line-h" style="bottom: ${percent}%"></div>`;
        yAxisLabels.innerHTML += `<div class="y-label" style="bottom: ${percent}%">${Math.round(balValue)}</div>`;
    }
    
    // Draw X-Axis (Day labels and 2-hour ticks)
    // 7 days = 168 hours
    for (let i = 0; i <= 168; i += 2) {
        const tickTime = minTime + (i * 60 * 60 * 1000);
        const percent = (i / 168) * 100;
        
        if (i % 24 === 0) {
            // Major tick / Day label
            gridLines.innerHTML += `<div class="grid-line-v" style="left: ${percent}%"></div>`;
            const dateObj = new Date(tickTime);
            xAxisLabels.innerHTML += `<div class="x-label" style="left: ${percent}%">${dateObj.toLocaleDateString(undefined, {month: 'short', day:'numeric'})}</div>`;
        } else {
            // Minor tick (2 hours)
            gridLines.innerHTML += `<div class="grid-line-v small-tick" style="left: ${percent}%"></div>`;
        }
    }
    
    let nodesHTML = '';
    
    // We will draw the SVG path using percentages!
    let pathData = '';
    
    recentPoints.forEach((p, i) => {
        const xPercent = ((p.date.getTime() - minTime) / (maxTime - minTime)) * 100;
        const yPercent = ((p.balance - minBal) / (maxBal - minBal)) * 100;
        
        // Ensure within bounds visually
        const safeX = Math.max(0, Math.min(100, xPercent));
        const safeY = Math.max(0, Math.min(100, yPercent));
        
        // For SVG, coords are absolute pixels. We'll map percentages to pixels in the timeout.
        p.xPercent = safeX;
        p.yPercent = safeY;
        
        if (p.isTx) {
            const dateStr = p.date.toLocaleDateString() + ' ' + p.date.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
            const typeLabel = p.type.replace('_', ' ');
            
            nodesHTML += `
                <div class="tx-node-wrapper tx-type-${p.type}" id="node-${i}" style="left: ${safeX}%; bottom: ${safeY}%;">
                    <div class="tx-node"></div>
                    <div class="tx-tooltip">
                        <div class="tx-tooltip-type">${typeLabel}</div>
                        <div class="tx-tooltip-amount">Rs. ${p.amount}</div>
                        <div class="tx-tooltip-date">${dateStr}</div>
                        <div class="tx-tooltip-date">Bal: Rs. ${p.balance}</div>
                    </div>
                </div>
            `;
        }
    });
    
    nodesContainer.innerHTML = nodesHTML;
    
    setTimeout(() => {
        const rect = gridLines.getBoundingClientRect();
        // Width and height of the plotting area
        const width = rect.width;
        const height = rect.height;
        // Offsets within the SVG viewBox
        const offsetX = 60; // left padding for axes
        const offsetY = 20; // top padding
        
        let d = '';
        recentPoints.forEach((p, i) => {
            const px = offsetX + (width * (p.xPercent / 100));
            const py = offsetY + (height * (1 - (p.yPercent / 100))); // SVG y is inverted
            
            if (i === 0) {
                d += `M ${px} ${py} `;
            } else {
                d += `L ${px} ${py} `;
            }
        });
        
        if (recentPoints.length > 1) {
            svgLines.innerHTML = `
                <path d="${d}" fill="none" stroke="var(--primary-color)" stroke-width="2" />
            `;
        }
    }, 50);
}
