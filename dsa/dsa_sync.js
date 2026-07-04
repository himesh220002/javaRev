import { defaultState as staticState } from '../data.js';

let state;
if (window.location.protocol.startsWith('http')) {
    try {
        const module = await import('../data.js?t=' + Date.now());
        state = module.defaultState;
        localStorage.setItem('learningTrackerState_v3', JSON.stringify(state));
    } catch (e) {
        state = JSON.parse(localStorage.getItem('learningTrackerState_v3')) || staticState;
    }
} else {
    state = JSON.parse(localStorage.getItem('learningTrackerState_v3')) || staticState;
}

window.saveState = async function () {
    localStorage.setItem('learningTrackerState_v3', JSON.stringify(state));
    try {
        await fetch('/save', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(state)
        });
    } catch (e) {
        console.log('Saved locally.');
    }
};

export function initSync(moduleName) {
    if (moduleName && state.DSA.children[moduleName]) {
        const grandchildren = state.DSA.children[moduleName].grandchildren;
        
        document.querySelectorAll('.topic-card').forEach(card => {
            const titleEl = card.querySelector('.topic-title');
            if (!titleEl) return;
            const title = titleEl.textContent.trim();
            const checkbox = card.querySelector('.checkbox');
            
            if (grandchildren[title]) {
                const isCompleted = grandchildren[title].completed;
                if (isCompleted) {
                    checkbox.classList.add('checked');
                    // Also check all subtopics to visually match
                    card.querySelectorAll('.subtopic input').forEach(cb => cb.checked = true);
                } else {
                    checkbox.classList.remove('checked');
                }
            }
            
            // Create a MutationObserver to detect when the 'checked' class is added/removed by user's scripts
            const observer = new MutationObserver((mutations) => {
                mutations.forEach((mutation) => {
                    if (mutation.attributeName === 'class') {
                        const isChecked = checkbox.classList.contains('checked');
                        if (grandchildren[title] && grandchildren[title].completed !== isChecked) {
                            grandchildren[title].completed = isChecked;
                            window.saveState();
                        }
                    }
                });
            });
            observer.observe(checkbox, { attributes: true });
        });

        // Trigger visual update
        if (typeof window.updateProgress === 'function') {
            document.querySelectorAll('.topic-card').forEach(card => window.updateProgress(card));
        } else {
            // Trigger manual input events to force user's scripts to update UI
            document.querySelectorAll('.subtopic input').forEach(cb => {
                cb.dispatchEvent(new Event('change'));
            });
        }
    } else {
        // dsa.html Overview logic
        document.querySelectorAll('.topic-item').forEach(item => {
            const nameEl = item.querySelector('.topic-name');
            if (!nameEl) return;
            const title = nameEl.textContent.trim();
            const checkbox = item.querySelector('.checkbox');
            
            let foundItem = null;
            for (const mod in state.DSA.children) {
                if (state.DSA.children[mod].grandchildren[title]) {
                    foundItem = state.DSA.children[mod].grandchildren[title];
                    break;
                }
            }
            
            if (foundItem) {
                if (foundItem.completed) {
                    checkbox.classList.add('checked');
                    const status = item.querySelector('.topic-status');
                    if(status) {
                        status.textContent = 'Done';
                        status.className = 'topic-status status-done';
                    }
                }
                
                const observer = new MutationObserver((mutations) => {
                    mutations.forEach((mutation) => {
                        if (mutation.attributeName === 'class') {
                            const isChecked = checkbox.classList.contains('checked');
                            if (foundItem.completed !== isChecked) {
                                foundItem.completed = isChecked;
                                window.saveState();
                            }
                        }
                    });
                });
                observer.observe(checkbox, { attributes: true });
            }
        });
        
        if (typeof window.updateProgress === 'function') {
            document.querySelectorAll('.category-card').forEach(card => window.updateProgress(card));
        }
    }
}
