import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'

console.log('main.jsx loaded');

const rootElement = document.getElementById('root')

if (!rootElement) {
  document.body.innerHTML = '<div style="padding: 20px; color: red;">Root element not found!</div>';
  throw new Error('Root element not found')
}

console.log('Root element found:', rootElement);

// Add a temporary visible element to confirm JS is running
rootElement.innerHTML = '<div id="loading" style="padding: 20px; background: yellow;">Loading React app...</div>';

try {
  console.log('Attempting to render App...');
  const loadingDiv = document.getElementById('loading');
  if (loadingDiv) loadingDiv.remove();
  
  createRoot(rootElement).render(
    <StrictMode>
      <App />
    </StrictMode>,
  )
  console.log('App rendered successfully');
} catch (error) {
  console.error('Error rendering app:', error)
  rootElement.innerHTML = '<div style="padding: 20px; color: red; background: #fee;">Error loading application: ' + error.message + '<br>Check console for details.</div>'
}
