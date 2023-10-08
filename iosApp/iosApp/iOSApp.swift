import SwiftUI
import enter

@main
struct iOSApp: App {

    init() {
        HelperKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}