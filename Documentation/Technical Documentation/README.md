# Source Code Documentation

## Purpose
The purpose of this documentation is to provide a structured overview of the key components of the HardwareCheck Android application, facilitating understanding, navigation, and maintenance for developers and other stakeholders.

## Audience
This documentation is designed for:
- Developers: To help understand the architecture, responsibilities, and functionality of each component
- Maintainers: To simplify debugging, enhancements, and updates
- New Contributors: To ease onboarding by providing clear insights into the system's design and behavior

## Screens
### OverviewScreen
Purpose: Main dashboard screen that displays device information and provides navigation to other features.

### RecentDevicesScreen
Purpose: Displays a list of recently checked devices with their information.

### ProfileScreen
Purpose: User profile management screen for settings and preferences.

### HardwareScreen
Purpose: Detailed hardware information display and analysis.

### CameraScreen
Purpose: Camera testing and information display.

### GuideScreen
Purpose: User guide and tutorial screen.

## Navigation
Purpose: Handle screen navigation and routing within the application.

### Components
- Navigation graph definition
- Route management
- Screen transitions
- Deep linking support

## Database
Purpose: Manage local data storage and persistence.

### Components
- Room database configuration
- Entity definitions
- DAO interfaces
- Database migrations

## Model
Purpose: Define data structures and business logic.

### Components
- Device information models
- User preferences
- Hardware specifications
- Camera details

## UI
Purpose: Provide reusable UI components and styling.

### Components
- Material Design 3 components
- Custom composables
- Theme definitions
- Layout components

## Utils
Purpose: Provide utility functions and helper classes.

### Components
- Location utilities
- Hardware detection
- Camera utilities
- Permission handling

## MainActivity
Purpose: Application entry point and main activity.

### Features
- Application initialization
- Permission management
- Navigation setup
- Lifecycle handling

## Security
Purpose: Handle application security and permissions.

### Components
- Permission management
- Secure storage
- Data encryption
- Access control

## Localization
Purpose: Manage application localization and internationalization.

### Features
- String resources
- Language selection
- Format handling
- RTL support

## Testing
Purpose: Support application testing and quality assurance.

### Components
- Unit tests
- UI tests
- Integration tests
- Test utilities 