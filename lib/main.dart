import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'bloc/dog_bloc.dart';
import 'bloc/dog_event.dart';
import 'bloc/dog_state.dart';
import 'repositories/dog_repository.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Random Dog Images',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        useMaterial3: true,
      ),
      home: BlocProvider(
        create: (context) => DogBloc(
          repository: DogRepository(),
        )..add(FetchDogImage()),
        child: const DogImagePage(),
      ),
    );
  }
}

class DogImagePage extends StatelessWidget {
  const DogImagePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Random Dog Images'),
      ),
      body: BlocBuilder<DogBloc, DogState>(
        builder: (context, state) {
          if (state is DogInitial) {
            return const Center(child: CircularProgressIndicator());
          } else if (state is DogLoading) {
            return const Center(child: CircularProgressIndicator());
          } else if (state is DogLoaded) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Image.network(
                    state.dogImage.message,
                    loadingBuilder: (context, child, loadingProgress) {
                      if (loadingProgress == null) return child;
                      return const CircularProgressIndicator();
                    },
                    errorBuilder: (context, error, stackTrace) {
                      return const Text('Failed to load image');
                    },
                  ),
                  const SizedBox(height: 20),
                  ElevatedButton(
                    onPressed: () {
                      context.read<DogBloc>().add(FetchDogImage());
                    },
                    child: const Text('Get Another Dog'),
                  ),
                ],
              ),
            );
          } else if (state is DogError) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text('Error: ${state.message}'),
                  const SizedBox(height: 20),
                  ElevatedButton(
                    onPressed: () {
                      context.read<DogBloc>().add(FetchDogImage());
                    },
                    child: const Text('Try Again'),
                  ),
                ],
              ),
            );
          }
          return const SizedBox.shrink();
        },
      ),
    );
  }
}
