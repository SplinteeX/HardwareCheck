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
Purpose: Main dashboard screen that displays device location and recently added devices.

### RecentDevicesScreen
Purpose: Displays a list of recently checked devices with their information.

### ProfileScreen
Purpose: User profile management screen for settings and preferences.

### HardwareScreen
Purpose: Detailed hardware information display.

### CameraScreen
Purpose: Camera testing (Lumination and Object Detection).

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
Purpose: Hold device data in FireBase.

#### Data Structure
- The database contains a collection named `devices`.
- Each device entry includes:
  - `Model`: Label or identifier for the device.
  - `osInfo`: Current state including OS version, security patch, and kernel info..
  - `Processor`: Processor details.
  - `Memory`: The amount of Memory and Physical Memory.
  - `Cores`: CPU Cores.
  - `Storage`: The amount of Storage.
  - `GPU`: GPU Information.
  - `Sensors`: Sensor data.
  - `Battery`: Gathers the battery status (%3).
  - `Uptime`: How long the phone has been on.
  - `Baseband`: Baseband version.
  - `BuildDate`: Build date.
  - `WifiVersion`: Latest data update for Wi-Fi version.
  - `BluetoothVersion`: Latest data update for Bluetooth Version.
  - `Timestamp`: Timestamp of the time when device has been added to Database.

#### Data Model
- A consistent data model is used to represent device entries.
- Ensures uniformity when interacting with the database.

#### Data Operations
- Devices can be added, removed, or retrieved using their unique ID.

## Model
Purpose: Define data structures and business logic.

### Components
- Device information models
- User preferences
- Hardware specifications
- Camera details

## UI
Purpose: UI components and styling.

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
